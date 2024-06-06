package com.github.lobakov.delivery.core.application.usecase.command.move.courier

import com.github.lobakov.delivery.api.adapters.http.exception.CourierNotFoundException
import com.github.lobakov.delivery.api.adapters.http.exception.OrderNotFoundException
import com.github.lobakov.delivery.core.application.usecase.shared.CommandHandler
import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus.CREATED
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import org.springframework.stereotype.Service

@Service
class MoveCourierHandler(
    private val orderRepository: OrderRepository,
    private val courierRepository: CourierRepository
) : CommandHandler<MoveCourierCommand> {

    override fun handle(command: MoveCourierCommand) {
        val courierId = command.orderId

        if (courierId == null) {
            val assignedOrders = orderRepository.getAllAssigned()

            courierRepository.getAllBusy().forEach { courier ->
                val matchingOrder = assignedOrders.firstOrNull { order ->
                    order.courierId == courier.id
                } ?: return@forEach

                moveAndUpdate(courier, matchingOrder)
            }
        } else {
            val courier = courierRepository.findById(courierId)
                ?: throw CourierNotFoundException("Courier with id $courierId not found")

            val order = orderRepository.findByCourierIdAndStatus(courierId, CREATED)
                ?: throw OrderNotFoundException(
                    "No orders found assigned to courier with id $courierId in status ${CREATED}"
                )

            moveAndUpdate(courier, order)
        }
    }

    private fun moveAndUpdate(courier: Courier, order: Order) {
        val destination = order.deliverTo
        courier.moveTo(destination)

        if (courier.hasReached(destination)) {
            courier.close(order)
        }

        courierRepository.update(courier)
        orderRepository.update(order)
    }
}
