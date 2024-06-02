package com.github.lobakov.delivery.api.adapters.http.mapper


import com.github.lobakov.delivery.api.adapters.http.model.Location
import com.github.lobakov.delivery.api.adapters.http.model.Order
import org.springframework.stereotype.Component

@Component
class OrderDtoMapper {

    fun orderToDto(orderAggregate: com.github.lobakov.delivery.core.domain.order.Order): Order {
        val order = Order()

        order.setId(orderAggregate.id)
        order.setCourierId(orderAggregate.courierId)
        order.setCourierLocation(locationToLocation(orderAggregate.courierLocation()))

        return order
    }

    private fun locationToDto(): Location {

    }
}
