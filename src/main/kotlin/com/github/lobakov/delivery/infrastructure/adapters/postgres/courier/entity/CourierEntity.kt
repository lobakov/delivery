package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.entity

import com.github.lobakov.delivery.core.domain.courier.CourierStatus
import com.github.lobakov.delivery.core.domain.courier.Transport
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import jakarta.persistence.*
import java.util.*

@Entity
@Table(schema = "delivery", name = "t_courier")
class CourierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Version
    var version: Long? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "status")
    var status: CourierStatus? = null

    @Column(name = "current_location")
    var currentLocation: Location? = null

    @Column(name = "transport")
    var transport: Transport? = null
}
