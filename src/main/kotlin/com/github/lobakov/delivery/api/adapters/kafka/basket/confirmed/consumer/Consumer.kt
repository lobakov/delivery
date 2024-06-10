package com.github.lobakov.delivery.api.adapters.kafka.basket.confirmed.consumer

import com.github.lobakov.delivery.core.application.usecase.command.create.order.CreateOrderCommand
import com.github.lobakov.delivery.core.application.usecase.command.create.order.CreateOrderHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class Consumer(
    private val createOrderHandler: CreateOrderHandler
) {

    @KafkaListener(topics = ["DeliveryConsumerGroup"])
    fun consume(event: BasketConfirmedIntegrationEvent?) {
        val basketId = UUID.fromString(event?.basketId)
        val address = event?.address
        val weight = event?.weight

        createOrderHandler.handle(
            CreateOrderCommand(basketId, address, weight)
        )
    }
}
