package com.github.lobakov.delivery.core.ports.courier

import com.github.lobakov.delivery.core.domain.courier.Courier

interface CourierRepository {

    fun add(courier: Courier): Courier

    fun update(courier: Courier): Courier

    fun getAllReady(): List<Courier>

    fun getAllBusy(): List<Courier>
}
