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
    lateinit var id: UUID

    @Column(name = "status")
    lateinit var status: OrderStatus

    @Column(name = "deliver_to")
    lateinit var deliverTo: Location

    @Column(name = "weight")
    lateinit var weight: Weight

    @Column(name = "courier_id")
    var courierId: UUID? = null
}
