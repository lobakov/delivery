package com.github.lobakov.delivery.core.application.usecase.query.get.all.incomplete.orders

import com.github.lobakov.delivery.core.application.usecase.shared.QueryHandler
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import org.springframework.stereotype.Service

@Service
class GetAllIncompleteOrdersHandler(
    private val repository: OrderRepository
) : QueryHandler<GetAllIncompleteOrdersCommand, List<Order>> {

    override fun handle(command: GetAllIncompleteOrdersCommand): List<Order> {
        val notAssignedOrders = repository.getAllNotAssigned()
        val assignedOrders = repository.getAllAssigned()

        return assignedOrders + notAssignedOrders
    }
}
