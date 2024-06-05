package com.github.lobakov.delivery.infrastructure.adapters.postgres.order.dto

import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import java.util.UUID

class OrderDto(val id: UUID, val courierId: UUID? = null, val courierLocation: Location? = null)
