package com.github.lobakov.delivery.core.domain.order

import com.github.lobakov.delivery.core.domain.order.OrderStatus.*
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import java.util.UUID

data class Order(
    val id: UUID,
    val deliverTo: Location,
    val weight: Weight
) {

    var version: Long = 1L

    var status: OrderStatus = CREATED
        private set(newStatus) {
            when (newStatus) {
                ASSIGNED -> {
                    require(status == CREATED) {
                        String.format(WRONG_STATUS_MESSAGE_TEMPLATE, newStatus, status, CREATED)
                    }
                    require(courierId != null) {
                        String.format("Cannot change order status to $ASSIGNED, courier has not been assigned.")
                    }
                    field = newStatus
                }

                COMPLETED -> {
                    require(status == ASSIGNED) {
                        String.format(WRONG_STATUS_MESSAGE_TEMPLATE, newStatus, status, ASSIGNED)
                    }
                    field = newStatus
                }

                else -> {
                    throw IllegalArgumentException("Unsupported order status $newStatus to transition to")
                }
            }
        }

    var courierId: UUID? = null
        private set(newCourierId) {
            require(status == CREATED) { "Cannot assign courier to order with status = $status" }
            require(courierId == null) { "Courier $courierId has been already assigned" }
            field = newCourierId
        }

    fun assign(courierId: UUID) {
        this.courierId = courierId
        status = ASSIGNED
    }

    fun complete() {
        status = COMPLETED
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Order
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        const val WRONG_STATUS_MESSAGE_TEMPLATE = "Cannot change status to {} when status = {}, required status is {}"
    }
}
