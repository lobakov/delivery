package com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.converter

import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class LocationConverter : AttributeConverter<Location, String> {

    override fun convertToDatabaseColumn(location: Location): String? {
        return try {
            String.format(FORMAT, location.x, location.y)
        } catch (e: Exception) {
            null
        }
    }

    override fun convertToEntityAttribute(dbData: String): Location? {
        val coordinateArray = dbData.split(SEPARATOR)
        val coordinateX = coordinateArray[0].toIntOrNull() ?: return null
        val coordinateY = coordinateArray[1].toIntOrNull() ?: return null
        return Location(coordinateX, coordinateY)
    }

    companion object {
        private const val FORMAT = "%d, %d"
        private const val SEPARATOR = ", "
    }
}
