package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.ports.courier.CourierRepository

class CourierRepositoryImpl(
    private val repository: CourierRepositoryJpa
) : CourierRepository {

    override fun add(courier: Courier): Courier {
        TODO("Not yet implemented")
    }

    override fun update(courier: Courier): Courier {
        TODO("Not yet implemented")
    }

    override fun getAllReady(): List<Courier> {
        TODO("Not yet implemented")
    }

    override fun getAllBusy(): List<Courier> {
        TODO("Not yet implemented")
    }
}