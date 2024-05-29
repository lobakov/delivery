package com.github.lobakov.delivery.core.ports.courier

import com.github.lobakov.delivery.core.domain.courier.Courier
import java.util.*

interface CourierRepository {

    fun add(courier: Courier): Courier

    fun update(courier: Courier)

    fun getAllReady(): List<Courier>

    fun getAllBusy(): List<Courier>

    fun findById(id: UUID): Courier
}
