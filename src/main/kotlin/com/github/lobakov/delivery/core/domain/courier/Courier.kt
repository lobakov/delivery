package com.github.lobakov.delivery.core.domain.courier

import com.github.lobakov.delivery.core.domain.courier.CourierStatus.*
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Location.Companion.INITIAL_LOCATION
import java.util.*

class Courier(
    val name: String,
    val transport: Transport,
    val id: UUID? = UUID.randomUUID()
) {

    var version: Long = 1L

    var currentLocation = INITIAL_LOCATION

    var status: CourierStatus = NOT_AVAILABLE
        private set(newStatus) {
            when (newStatus) {
                status -> {
                    throw IllegalArgumentException("Cannot change status, courier is already in $newStatus status")
                }

                READY -> field = newStatus

                NOT_AVAILABLE, BUSY -> {
                    require(status == READY) {
                        String.format(WRONG_STATUS_MESSAGE_TEMPLATE, newStatus, status, READY)
                    }
                    field = newStatus
                }

                else -> {
                    throw IllegalArgumentException("Unsupported order status $newStatus to transition to")
                }
            }
        }

    fun startWorkingDay() {
        require(status == NOT_AVAILABLE) {
            "Working day has already started"
        }
        status = READY
    }

    fun finishWorkingDay() {
        require(status == READY) {
            "Cannot finish the working day, the courier is in $status status"
        }
        status = READY
    }

    fun assign(order: Order) {
        require(status == READY) {
            "Cannot assign the order ${order.id} to courier, the courier is in $status status"
        }
        order.assign(this.id!!)
        status = BUSY
    }

    fun moveTo(destination: Location) {
        currentLocation = currentLocation.moveTo(destination, transport.speed)
    }

    fun countStepsTo(destination: Location): Int {
        val distance = currentLocation.distanceTo(destination)
        val speed = transport.speed

        val numberOfSteps = distance / speed
        val extraStep = if ((distance % speed) != 0) 1 else 0

        return numberOfSteps + extraStep
    }

    fun hasReached(destination: Location): Boolean {
        return currentLocation.distanceTo(destination) == 0
    }

    fun close(order: Order) {
        order.complete()
        status = READY
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Courier
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        const val WRONG_STATUS_MESSAGE_TEMPLATE = "Cannot change status to {} when status = {}, required status is {}"
    }
}
