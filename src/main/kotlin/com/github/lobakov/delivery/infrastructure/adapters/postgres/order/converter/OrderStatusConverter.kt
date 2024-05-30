package com.github.lobakov.delivery.infrastructure.adapters.postgres.order.converter

import com.github.lobakov.delivery.core.domain.order.OrderStatus
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class OrderStatusConverter : AttributeConverter<OrderStatus, String> {

    override fun convertToDatabaseColumn(status: OrderStatus): String {
        return status.value
    }

    override fun convertToEntityAttribute(dbData: String): OrderStatus? {
        return try {
            OrderStatus[dbData]
        } catch (e: Exception) {
            null
        }
    }
}
