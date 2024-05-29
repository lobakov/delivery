package com.github.lobakov.delivery.infrastructure.adapters.postgres.order

import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus
import com.github.lobakov.delivery.core.domain.order.OrderStatus.ASSIGNED
import com.github.lobakov.delivery.core.domain.order.OrderStatus.CREATED
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import com.github.lobakov.delivery.infrastructure.adapters.postgres.order.mapper.OrderMapper
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderRepositoryImpl(
    private val repository: OrderRepositoryJpa,
    private val mapper: OrderMapper
): OrderRepository {

    @Transactional
    override fun add(order: Order): Order {
        val orderEntity = mapper.toEntity(order)
        val savedEntity = repository.saveAndFlush(orderEntity)
        return mapper.toAggregate(savedEntity)
    }

    @Transactional
    override fun update(order: Order) {
        val reference = repository.getReferenceById(order.id)
        val entity = mapper.updateEntity(order, reference)
        repository.saveAndFlush(entity)
    }

    override fun findById(id: UUID): Order {
        return repository.findById(id)
            .map { mapper.toAggregate(it) }
            .orElse(null)
    }

    override fun getAllNotAssigned(): List<Order> {
        return getAllByStatus(CREATED)
    }

    override fun getAllAssigned(): List<Order> {
        return getAllByStatus(ASSIGNED)
    }

    private fun getAllByStatus(status: OrderStatus): List<Order> {
        return repository
            .findAllByStatus(status)
            .map { mapper.toAggregate(it) }
    }
}
