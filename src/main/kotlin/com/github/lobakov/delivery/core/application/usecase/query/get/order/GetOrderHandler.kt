package com.github.lobakov.delivery.core.application.usecase.query.get.order

import com.github.lobakov.delivery.api.adapters.http.exception.CourierNotFoundException
import com.github.lobakov.delivery.api.adapters.http.exception.OrderNotFoundException
import com.github.lobakov.delivery.core.application.usecase.shared.QueryHandler
import com.github.lobakov.delivery.infrastructure.adapters.postgres.order.dto.OrderDto
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.RepositoryFacade
import org.springframework.stereotype.Service

@Service
class GetOrderHandler(
    private val repositoryFacade: RepositoryFacade
) : QueryHandler<GetOrderQuery, OrderDto> {

    override fun handle(query: GetOrderQuery): OrderDto {
        val order = repositoryFacade.getOrderById(query.orderId)
            ?: throw OrderNotFoundException("No order with order id = ${query.orderId}")

        return if (order.courierId != null) {
            val courier = repositoryFacade.getCourierById(order.courierId!!)
                ?: throw CourierNotFoundException("Courier with id = ${order.courierId} not found")

            OrderDto(
                id = order.id,
                courierId = courier.id,
                courierLocation = courier.currentLocation
            )
        } else {
            OrderDto(id = order.id)
        }
    }
}
