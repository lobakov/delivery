package com.github.lobakov.delivery.core.application.query.get.all.incomplete.orders

import com.github.lobakov.delivery.core.application.shared.QueryHandler
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.ports.order.OrderRepository

class GetAllIncompleteOrdersHandler(
    private val repository: OrderRepository
) : QueryHandler<GetAllIncompleteOrdersCommand, List<Order>> {

    override fun handle(command: GetAllIncompleteOrdersCommand): List<Order> {
        val notAssignedOrders = repository.getAllNotAssigned()
        val assignedOrders = repository.getAllAssigned()

        return assignedOrders + notAssignedOrders
    }
}
