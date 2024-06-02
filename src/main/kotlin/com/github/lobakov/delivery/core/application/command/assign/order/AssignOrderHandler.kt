package com.github.lobakov.delivery.core.application.command.assign.order

import com.github.lobakov.delivery.core.application.shared.CommandHandler
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
        orderRepository.getAllNotAssigned()
            .forEach {
                val readyCouriers = courierRepository.getAllReady()

                val courier = dispatcher.dispatch(it, readyCouriers)
                    ?: return@forEach //if no courier found for this order - continue

                courier.assign(it)

                courierRepository.update(courier)
                orderRepository.update(it)
            }
    }
}
