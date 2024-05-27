package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.entity

import com.github.lobakov.delivery.core.domain.courier.CourierStatus
import com.github.lobakov.delivery.core.domain.courier.Transport
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import jakarta.persistence.*
import java.util.*

@Entity
@Table(schema = "delivery", name = "t_courier")
class CourierEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @Column(name = "status")
    lateinit var status: CourierStatus

    @Column(name = "current_location")
    lateinit var currentLocation: Location

    @Column(name = "transport")
    lateinit var transport: Transport
}
