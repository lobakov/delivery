package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.mapper

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.courier.CourierStatus.BUSY
import com.github.lobakov.delivery.core.domain.courier.CourierStatus.READY
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.entity.CourierEntity
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.mapper.EntityMapper
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel.SPRING
import java.util.*

typealias DoNothing = Unit

@Mapper(componentModel = SPRING)
class CourierMapper : EntityMapper<Courier, CourierEntity> {

    override fun toAggregate(entity: CourierEntity): Courier {
        val aggregate = Courier(
            id = entity.id!!,
            name = entity.name!!,
            transport = entity.transport!!
        )

        aggregate.version = entity.version!!
        aggregate.currentLocation = entity.currentLocation!!

        when (entity.status) {
            READY -> aggregate.startWorkingDay()
            BUSY -> {
                aggregate.startWorkingDay()
                aggregate.assign( //the only way to set READY status is to assign dummy order
                    Order(
                        UUID.randomUUID(),
                        Location(1,1),
                        Weight(1)
                    )
                )
            }
            else -> DoNothing
        }

        return aggregate
    }

    override fun toEntity(aggregate: Courier): CourierEntity {
        val entity = CourierEntity()

        entity.id = aggregate.id
        entity.version = aggregate.version
        entity.name = aggregate.name
        entity.currentLocation = aggregate.currentLocation
        entity.status = aggregate.status
        entity.transport = aggregate.transport

        return entity
    }

    override fun updateEntity(aggregate: Courier, entity: CourierEntity): CourierEntity {
        entity.version = aggregate.version
        entity.currentLocation = aggregate.currentLocation
        entity.status = aggregate.status

        return entity
    }
}
