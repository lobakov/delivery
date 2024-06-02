package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.courier.CourierStatus
import com.github.lobakov.delivery.core.domain.courier.CourierStatus.BUSY
import com.github.lobakov.delivery.core.domain.courier.CourierStatus.READY
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.mapper.CourierMapper
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourierRepositoryImpl(
    private val repository: CourierRepositoryJpa,
    private val mapper: CourierMapper
) : CourierRepository {

    @Transactional
    override fun add(courier: Courier): Courier {
        val courierEntity = mapper.toEntity(courier)
        val savedEntity = repository.saveAndFlush(courierEntity)
        return mapper.toAggregate(savedEntity)
    }

    @Transactional
    override fun update(courier: Courier) {
        val reference = repository.getReferenceById(courier.id!!)
        val entity = mapper.updateEntity(courier, reference)
        repository.saveAndFlush(entity)
    }

    override fun getAllReady(): List<Courier> {
        return getAllByStatus(READY)
    }

    override fun getAllBusy(): List<Courier> {
        return getAllByStatus(BUSY)
    }

    override fun findById(id: UUID): Courier? {
        return repository.findById(id)
            .map { mapper.toAggregate(it) }
            .orElse(null)
    }

    private fun getAllByStatus(status: CourierStatus): List<Courier> {
        return repository.findAllByStatus(status)
            .map { mapper.toAggregate(it) }
    }
}
