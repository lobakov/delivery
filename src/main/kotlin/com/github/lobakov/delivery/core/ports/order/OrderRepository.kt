package com.github.lobakov.delivery.core.ports.order

import com.github.lobakov.delivery.core.domain.order.Order
import java.util.*

interface OrderRepository {

    fun add(order: Order): Order

    fun update(order: Order)

    fun getAllNotAssigned(): List<Order>

    fun getAllAssigned(): List<Order>

    fun findById(id: UUID): Order?
}
