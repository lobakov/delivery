package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.converter

import com.github.lobakov.delivery.core.domain.courier.CourierStatus
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class CourierStatusConverter : AttributeConverter<CourierStatus, String> {

    override fun convertToDatabaseColumn(status: CourierStatus): String {
        return status.value
    }

    override fun convertToEntityAttribute(dbData: String): CourierStatus? {
        return try {
            CourierStatus[dbData]
        } catch (e: Exception) {
            null
        }
    }
}
