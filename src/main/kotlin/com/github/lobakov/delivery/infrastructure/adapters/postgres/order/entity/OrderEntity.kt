package com.github.lobakov.delivery.infrastructure.adapters.postgres.order.entity

import com.github.lobakov.delivery.core.domain.order.OrderStatus
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import com.github.lobakov.delivery.infrastructure.adapters.postgres.order.converter.OrderStatusConverter
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.converter.LocationConverter
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.converter.WeightConverter
import jakarta.persistence.*
import java.util.*

@Entity
@Table(schema = "delivery", name = "t_order")
class OrderEntity {

    @Id
    var id: UUID? = null

    @Version
    var version: Long? = null

    @Column(name = "status")
    @Convert(converter = OrderStatusConverter::class)
    var status: OrderStatus? = null

    @Column(name = "deliver_to")
    @Convert(converter = LocationConverter::class)
    var deliverTo: Location? = null

    @Column(name = "weight")
    @Convert(converter = WeightConverter::class)
    var weight: Weight? = null

    @Column(name = "courier_id")
    var courierId: UUID? = null
}
