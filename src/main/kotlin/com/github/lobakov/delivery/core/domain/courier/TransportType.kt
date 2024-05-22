package com.github.lobakov.delivery.core.domain.courier

import com.github.lobakov.delivery.core.domain.sharedkernel.Weight

enum class TransportType(
    val typeName: String,
    val speed: Int,
    val capacity: Weight
) {
    PEDESTRIAN("Пешеход", 1, Weight(1)),
    BYCICLE("Велосипед", 2, Weight(4)),
    SCOOTER("Мопед", 3, Weight(6)),
    CAR("Автомобиль", 4, Weight(8));

    companion object {
        private val statusMap = entries.associateBy { it.typeName }
        operator fun get(value: String) = statusMap[value]
    }
}