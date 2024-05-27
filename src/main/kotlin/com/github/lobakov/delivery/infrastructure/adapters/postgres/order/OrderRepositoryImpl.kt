package com.github.lobakov.delivery.infrastructure.adapters.postgres.order

import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus.ASSIGNED
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.mapper.OrderMapper
import org.springframework.stereotype.Service

@Service
class OrderRepositoryImpl(
    private val repository: OrderRepositoryJpa,
    private val mapper: OrderMapper
): OrderRepository {
    override fun add(order: Order): Order {

    }

    override fun update(order: Order): Order {

    }

    override fun getAllNotAssigned(): List<Order> {

    }

    override fun getAllAssigned(): List<Order> {
        return repository
            .findAllByStatus(ASSIGNED)
            .map { mapper.toAggregate(it) }
    }
}