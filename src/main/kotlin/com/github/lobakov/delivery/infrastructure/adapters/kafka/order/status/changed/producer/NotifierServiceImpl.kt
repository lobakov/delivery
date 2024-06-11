package com.github.lobakov.delivery.infrastructure.adapters.kafka.order.status.changed.producer

import com.github.lobakov.delivery.core.ports.notifier.NotifierService
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NotifierServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String, OrderStatusChangedIntegrationEvent>
) : NotifierService {

    override fun notify(orderId: UUID, newStatus: com.github.lobakov.delivery.core.domain.order.OrderStatus) {
        val orderStatusChangedEvent = orderStatusChangedIntegrationEvent {
            this.orderId = orderId.toString()
            this.orderStatus =
                com.github.lobakov.delivery.infrastructure.adapters.kafka.order.status.changed.producer.OrderStatus.forNumber(newStatus.id)
        }

        try {
            kafkaTemplate.send(
                orderStatusChangedEventTopicKey,
                orderStatusChangedEvent
            )
        } catch (e: Exception) {
            LOGGER.error(
                "Failed to send order status changed event, orderId {}, new status {} error: {}",
                orderId, newStatus.toString(), e.localizedMessage
            )
        }
    }

    companion object {
        private const val orderStatusChangedEventTopicKey = "order.status.changed"
        private val LOGGER = LoggerFactory.getLogger(NotifierService::class.java)
    }
}
