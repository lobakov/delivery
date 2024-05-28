package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.mapper

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus.COMPLETED
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.entity.CourierEntity
import com.github.lobakov.delivery.infrastructure.adapters.postgres.order.entity.OrderEntity
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.mapper.EntityMapper
import org.mapstruct.Mapper
import org.mapstruct.NullValuePropertyMappingStrategy
import java.util.*

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
interface CourierMapper : EntityMapper<Courier, OrderEntity> {

    override fun toAggregate(entity: CourierEntity): Courier {
        val aggregate = Courier(
            name = entity.name!!,
            transport = entity.transport!!
        )

        aggregate.version = entity.version!!
        aggregate.currentLocation = entity.currentLocation!!
        aggregate.status = entity.status!!

        when

        if (entity. == COMPLETED) {

        }

        return aggregate
    }

    override fun toEntity(aggregate: Courier): CourierEntity {
        val entity = CourierEntity()

        entity.id = aggregate.id
        entity.version = aggregate.version
        entity.deliverTo = aggregate.deliverTo
        entity.weight = aggregate.weight
        entity.status = aggregate.status
        entity.courierId = aggregate.courierId

        return entity
    }
}
