package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.mapper

import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus.COMPLETED
import com.github.lobakov.delivery.infrastructure.adapters.postgres.order.entity.OrderEntity
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.mapper.Mapper

class OrderMapper : Mapper<Order, OrderEntity> {

    override fun toAggregate(entity: OrderEntity): Order {
        val order = Order(
            id = entity.id,
            deliverTo = entity.deliverTo,
            weight = entity.weight
        )

        if (entity.courierId != null) {
            order.assign(entity.courierId!!)
        }

        if (entity.status == COMPLETED) {
            order.complete()
        }

        return order
    }

    override fun toEntity(aggregate: Order): OrderEntity {
        val entity = OrderEntity()
    }


}