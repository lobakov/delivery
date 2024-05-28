package com.github.lobakov.delivery.infrastructure.adapters.postgres.order

import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus.ASSIGNED
import com.github.lobakov.delivery.core.domain.order.OrderStatus.CREATED
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.mapper.CourierMapper
import org.springframework.stereotype.Service

@Service
class OrderRepositoryImpl(
    private val repository: OrderRepositoryJpa,
    private val mapper: CourierMapper
): OrderRepository {

    override fun add(order: Order): Order {

    }

    override fun update(order: Order): Order {

    }

    override fun getAllNotAssigned(): List<Order> {
        return repository
            .findAllByStatus(CREATED)
            .map { mapper.toAggregate(it) }
    }

    override fun getAllAssigned(): List<Order> {
        return repository
            .findAllByStatus(ASSIGNED)
            .map { mapper.toAggregate(it) }
    }
}
