package com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.converter

import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class WeightConverter : AttributeConverter<Weight, Int> {

    override fun convertToDatabaseColumn(weight: Weight): Int {
        return weight.kg
    }

    override fun convertToEntityAttribute(kg: Int): Weight? {
        return try {
            Weight(kg)
        } catch (e: Exception) {
            null
        }
    }
}
