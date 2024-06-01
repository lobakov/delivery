package com.github.lobakov.delivery.core.application.command.move.courier

import com.github.lobakov.delivery.core.application.shared.CommandHandler
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import org.springframework.stereotype.Service

@Service
class MoveCourierHandler(
    private val orderRepository: OrderRepository,
    private val courierRepository: CourierRepository
) : CommandHandler<MoveCourierCommand> {

    override fun handle(command: MoveCourierCommand) {
        val assignedOrders = orderRepository.getAllAssigned()

        courierRepository.getAllBusy().forEach { courier ->
            val matchingOrder = assignedOrders.firstOrNull {
                order -> order.courierId == courier.id
            } ?: return@forEach

            courier.moveTo(matchingOrder.deliverTo)
        }
    }
}
