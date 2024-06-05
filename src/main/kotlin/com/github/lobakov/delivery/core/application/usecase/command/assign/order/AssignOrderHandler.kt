package com.github.lobakov.delivery.core.application.usecase.command.assign.order


import com.github.lobakov.delivery.core.application.usecase.shared.CommandHandler
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domainservices.DispatchService
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import org.springframework.stereotype.Service

@Service
class AssignOrderHandler(
    private val dispatcher: DispatchService,
    private val orderRepository: OrderRepository,
    private val courierRepository: CourierRepository
) : CommandHandler<AssignOrderCommand> {

    override fun handle(command: AssignOrderCommand) {
        val orderId = command.orderId

        if (orderId == null) {
            orderRepository.getAllNotAssigned()
                .forEach {
                    assign(it)
                }
        } else {
            val order = orderRepository.findById(orderId)
                ?: throw IllegalStateException("Order $orderId not found")
            assign(order)
        }
    }

    private fun assign(order: Order) {
        val readyCouriers = courierRepository.getAllReady()

        val courier = dispatcher.dispatch(order, readyCouriers)
            ?: return

        courier.assign(order)

        courierRepository.update(courier)
        orderRepository.update(order)
    }
}