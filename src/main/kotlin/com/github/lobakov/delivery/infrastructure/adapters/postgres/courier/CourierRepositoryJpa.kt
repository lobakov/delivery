package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier

import com.github.lobakov.delivery.core.domain.courier.CourierStatus
import com.github.lobakov.delivery.infrastructure.adapters.postgres.courier.entity.CourierEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CourierRepositoryJpa : JpaRepository<CourierEntity, UUID> {

    fun findAllByStatus(status: CourierStatus): List<CourierEntity>
}
