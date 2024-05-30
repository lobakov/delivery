package com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.converter

import com.github.lobakov.delivery.core.domain.courier.Transport
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class TransportConverter : AttributeConverter<Transport, Int> {

    override fun convertToDatabaseColumn(transport: Transport): Int {
        return transport.id
    }

    override fun convertToEntityAttribute(id: Int): Transport? {
        return try {
            Transport.get(id)
        } catch (e: Exception) {
            null
        }
    }
}
