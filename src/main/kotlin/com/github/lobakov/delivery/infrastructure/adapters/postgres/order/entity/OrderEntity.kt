package com.github.lobakov.delivery.infrastructure.adapters.postgres.order.entity

import com.github.lobakov.delivery.core.domain.order.OrderStatus
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import jakarta.persistence.*
import java.util.*

@Entity
@Table(schema = "delivery", name = "t_order")
class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Version
    var version: Long? = null

    @Column(name = "status")
    var status: OrderStatus? = null

    @Column(name = "deliver_to")
    var deliverTo: Location? = null

    @Column(name = "weight")
    var weight: Weight? = null

    @Column(name = "courier_id")
    var courierId: UUID? = null
}
