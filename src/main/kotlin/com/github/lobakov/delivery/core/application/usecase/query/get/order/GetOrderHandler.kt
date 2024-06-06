package com.github.lobakov.delivery.core.application.usecase.query.get.order

import com.github.lobakov.delivery.api.adapters.http.exception.CourierNotFoundException
import com.github.lobakov.delivery.api.adapters.http.exception.OrderNotFoundException
import com.github.lobakov.delivery.core.application.usecase.shared.QueryHandler
import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import com.github.lobakov.delivery.infrastructure.adapters.postgres.order.dto.OrderDto
import org.springframework.stereotype.Service

@Service
class GetOrderHandler(
    private val orderRepository: OrderRepository,
    private val courierRepository: CourierRepository
) : QueryHandler<GetOrderCommand, OrderDto> {

    override fun handle(command: GetOrderCommand): OrderDto {
        val order = orderRepository.findById(command.orderId)
            ?: throw OrderNotFoundException("No order with order id = ${command.orderId}")

        return if (order.courierId != null) {
            val courier = courierRepository.findById(order.courierId!!)
                ?: CourierNotFoundException("Courier with id = ${order.courierId} not found")

            OrderDto(
                id = order.id,
                courierId = (courier as Courier).id,
                courierLocation = courier.currentLocation
            )
        } else {
            OrderDto(id = order.id)
        }
    }
}
