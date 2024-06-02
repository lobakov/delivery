package com.github.lobakov.delivery.core.application.command.create.order

import com.github.lobakov.delivery.core.application.shared.CommandHandler
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import org.springframework.stereotype.Service

const val SEPARATOR = ", "

@Service
class CreateOrderHandler(
    private val repository: OrderRepository
) : CommandHandler<CreateOrderCommand> {

    override fun handle(command: CreateOrderCommand) {
        repository.findById(command.basketId)
            ?: {
                val orderId = command.basketId
                val location = addressToLocation(command.address)
                val weight = Weight(command.weight)

                val order = Order(orderId, location, weight)

                repository.add(order)
            }
    }

    private fun addressToLocation(address: String): Location {
        val coordinates = address
            .split(SEPARATOR)
            .map { it.toIntOrNull() }
            .requireNoNulls()

        return Location(coordinates[0], coordinates[1])
    }
}
