package com.github.lobakov.delivery.core.application.usecase.command.create.order

import com.github.lobakov.delivery.core.application.usecase.shared.CommandHandler
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateOrderHandler(
    private val repository: OrderRepository
) : CommandHandler<CreateOrderCommand> {

    override fun handle(command: CreateOrderCommand) {
        val basketId = command.basketId

        if (basketId == null) {
            repository.add(
                Order(
                    id = UUID.randomUUID(),
                    deliverTo = Location.getRandomLocation(),
                    weight = Weight.getRandomWeight()
                )
            )
        } else {
            repository.findById(command.basketId)
                ?: {
                    val orderId = command.basketId
                    val location = Location.fromAddress(command.address!!)
                    val weight = Weight(command.weight!!)

                    val order = Order(orderId, location, weight)

                    repository.add(order)
                }
        }
    }
}
