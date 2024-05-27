package com.github.lobakov.delivery.core.domain.courier

import com.github.lobakov.delivery.core.domain.sharedkernel.Weight

enum class Transport(
    val id: Int,
    val transportName: String,
    val speed: Int,
    val capacity: Weight
) {
    PEDESTRIAN(1, "Пешеход", 1, Weight(1)),
    BYCICLE(2, "Велосипед", 2, Weight(4)),
    SCOOTER(3, "Мопед", 3, Weight(6)),
    CAR(4, "Автомобиль", 4, Weight(8));

    companion object {
        private val statusMap = entries.associateBy { it.transportName }
        operator fun get(value: String) = statusMap[value]
        fun get(id: Int) = entries.find { id == it.id }
    }

    fun canCarry(cargoWeight: Weight): Boolean = capacity >= cargoWeight
}
