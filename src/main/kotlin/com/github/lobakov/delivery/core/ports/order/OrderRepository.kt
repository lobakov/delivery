package com.github.lobakov.delivery.core.ports.order

import com.github.lobakov.delivery.core.domain.order.Order

interface OrderRepository {

    fun add(order: Order): Order

    fun update(order: Order): Order

    fun getAllNotAssigned(): List<Order>

    fun getAllAssigned(): List<Order>
}
