package com.github.lobakov.delivery.core.domain.courier

import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import java.util.UUID

class Transport(
    transportType: TransportType
) {
    val id = UUID.randomUUID()
    val name = transportType.typeName
    val speed = transportType.speed
    val capacity = transportType.capacity

    fun canCarry(cargoWeight: Weight): Boolean = capacity >= cargoWeight
}
