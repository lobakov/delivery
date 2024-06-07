package com.github.lobakov.delivery.core.application.usecase.query.get.all.incomplete.orders

import com.github.lobakov.delivery.core.application.usecase.shared.QueryHandler
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.RepositoryFacade
import org.springframework.stereotype.Service

@Service
class GetAllIncompleteOrdersHandler(
    private val repositoryFacade: RepositoryFacade
) : QueryHandler<GetAllIncompleteOrdersQuery, List<Order>> {

    override fun handle(query: GetAllIncompleteOrdersQuery): List<Order> {
        val notAssignedOrders = repositoryFacade.getAllNotAssignedOrders()
        val assignedOrders = repositoryFacade.getAllAssignedOrders()

        return assignedOrders + notAssignedOrders
    }
}
