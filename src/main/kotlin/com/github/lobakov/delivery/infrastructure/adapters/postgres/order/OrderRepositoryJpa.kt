package com.github.lobakov.delivery.infrastructure.adapters.postgres.order

import com.github.lobakov.delivery.core.domain.order.OrderStatus
import com.github.lobakov.delivery.infrastructure.adapters.postgres.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderRepositoryJpa : JpaRepository<OrderEntity, UUID> {

    fun findAllByStatus(status: OrderStatus): List<OrderEntity>
}
