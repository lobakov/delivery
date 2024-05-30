package com.github.lobakov.delivery.infrastructure.adapters.postgres.order.mapper

import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus.COMPLETED
import com.github.lobakov.delivery.infrastructure.adapters.postgres.order.entity.OrderEntity
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.mapper.EntityMapper
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel.SPRING

@Mapper(componentModel = SPRING)
class OrderMapper : EntityMapper<Order, OrderEntity> {

    override fun toAggregate(entity: OrderEntity): Order {
        val order = Order(
            id = entity.id!!,
            deliverTo = entity.deliverTo!!,
            weight = entity.weight!!
        )

        order.version = entity.version!!

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

        entity.id = aggregate.id
        entity.version = aggregate.version
        entity.deliverTo = aggregate.deliverTo
        entity.weight = aggregate.weight
        entity.status = aggregate.status
        entity.courierId = aggregate.courierId

        return entity
    }

    override fun updateEntity(aggregate: Order, entity: OrderEntity): OrderEntity {
        entity.status = aggregate.status
        entity.courierId = aggregate.courierId

        return entity
    }
}
