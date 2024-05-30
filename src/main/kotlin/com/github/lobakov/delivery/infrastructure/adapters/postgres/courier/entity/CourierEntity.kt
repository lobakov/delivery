package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.entity

import com.github.lobakov.delivery.core.domain.courier.CourierStatus
import com.github.lobakov.delivery.core.domain.courier.Transport
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.converter.CourierStatusConverter
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.converter.LocationConverter
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.converter.TransportConverter
import jakarta.persistence.*
import java.util.*

@Entity
@Table(schema = "delivery", name = "t_courier")
class CourierEntity {

    @Id
    var id: UUID? = null

    @Version
    var version: Long? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "status")
    @Convert(converter = CourierStatusConverter::class)
    var status: CourierStatus? = null

    @Column(name = "current_location")
    @Convert(converter = LocationConverter::class)
    var currentLocation: Location? = null

    @Column(name = "transport")
    @Convert(converter = TransportConverter::class)
    var transport: Transport? = null
}
