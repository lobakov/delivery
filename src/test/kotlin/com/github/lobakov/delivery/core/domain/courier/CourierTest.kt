package com.github.lobakov.delivery.core.domain.courier

import com.github.lobakov.delivery.core.domain.courier.CourierStatus.*
import com.github.lobakov.delivery.core.domain.courier.TransportType.CAR
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Location.Companion.INITIAL_LOCATION
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class CourierTest {

    @Test
    fun `courier should be initialized with default location and NOT AVAILABLE status on creation`() {
        //Given, When
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)

        //Then
        assertAll(
            "Ensure courier was initialized properly",
            { assertEquals(DEFAULT_NAME, sut.name) },
            { assertEquals(DEFAULT_TRANSPORT, sut.transport) },
            { assertEquals(NOT_AVAILABLE, sut.status) },
            { assertEquals(DEFAULT_LOCATION, sut.currentLocation) },
        )
    }

    @Test
    fun `courier should transition to READY status when starting working day`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)

        //When
        sut.startWorkingDay()

        //Then
        assertEquals(READY, sut.status)
    }

    @Test
    fun `courier should throw exception on starting working day when working day has already started`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        sut.startWorkingDay()

        //When, Then
        val actualException = assertThrows<IllegalArgumentException> {
            sut.startWorkingDay()
        }
        assertEquals("Working day has already started", actualException.message)
    }

    @Test
    fun `courier should throw exception on finishing working day when working day has not started`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)

        //When, Then
        val actualException = assertThrows<IllegalArgumentException> {
            sut.finishWorkingDay()
        }
        assertEquals(
            "Cannot finish the working day, the courier is in ${sut.status} status",
            actualException.message
        )
    }

    @Test
    fun `courier should transition to BUSY status when order assigned`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        val order = Order(UUID.randomUUID(), DEFAULT_LOCATION, DEFAULT_WEIGHT)

        //When
        sut.startWorkingDay()
        sut.assign(order)

        //Then
        assertEquals(BUSY, sut.status)
    }

    @Test
    fun `courier should transition to READY status when order is delivered`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        val order = Order(UUID.randomUUID(), DEFAULT_LOCATION, DEFAULT_WEIGHT)
        sut.startWorkingDay()
        sut.assign(order)

        //When
        sut.close(order)

        //Then
        assertEquals(READY, sut.status)
    }

    @Test
    fun `courier should throw exception on finishing working day when delivering goods`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        sut.startWorkingDay()

        val order = Order(UUID.randomUUID(), DEFAULT_LOCATION, DEFAULT_WEIGHT)
        sut.assign(order)

        //When, Then
        val actualException = assertThrows<IllegalArgumentException> {
            sut.finishWorkingDay()
        }
        assertEquals(
            "Cannot finish the working day, the courier is in ${sut.status} status",
            actualException.message
        )
    }

    @Test
    fun `courier should properly determine step count`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        val order = Order(UUID.randomUUID(), CENTER_LOCATION, DEFAULT_WEIGHT)
        sut.startWorkingDay()
        sut.assign(order)

        //When
        val actualStepCount = sut.countStepsTo(order.deliverTo)

        //Then
        assertEquals(2, actualStepCount)
    }

    @Test
    fun `courier should properly determine when destination has been reached`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        sut.startWorkingDay()

        val order = Order(UUID.randomUUID(), CENTER_LOCATION, DEFAULT_WEIGHT)
        sut.assign(order)

        val destination = order.deliverTo
        var stepCount = sut.countStepsTo(destination)

        //When
        while (stepCount-- > 0) {
           sut.moveTo(destination)
        }

        //Then
        assertEquals(true, sut.hasReached(destination))
    }

    @Test
    fun `courier should move up and left direction when direction is up and left`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        sut.currentLocation = Location(4, 5)
        sut.startWorkingDay()

        val order = Order(UUID.randomUUID(), NEARER_LOCATION, DEFAULT_WEIGHT)
        sut.assign(order)

        //When
        sut.moveTo(order.deliverTo)

        //Then
        assertEquals(Location(1, 4), sut.currentLocation)
    }

    @Test
    fun `courier should move down and forward direction when direction is down and forward`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        sut.currentLocation = Location(7, 6)
        val order = Order(UUID.randomUUID(), FARTHER_LOCATION, DEFAULT_WEIGHT)
        val destination = order.deliverTo

        sut.startWorkingDay()
        sut.assign(order)

        //When
        sut.moveTo(destination)

        //Then
        assertEquals(Location(10, 7), sut.currentLocation)

    }

    @Test
    fun `courier should determine when destination reached if speed higher than amount of steps`() {
        //Given
        val sut = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        sut.currentLocation = CENTER_LOCATION
        sut.startWorkingDay()

        val order = Order(UUID.randomUUID(), FARTHER_LOCATION, DEFAULT_WEIGHT)
        sut.assign(order)

        val destination = order.deliverTo
        var stepCount = sut.countStepsTo(destination)

        //When
        while (stepCount-- > 0) {
            sut.moveTo(destination)
        }

        assertAll(
            "Ensure the courier reached correct destination",
            { assertEquals(destination, sut.currentLocation) },
            { assertEquals(true, sut.hasReached(destination)) }
        )
    }

    companion object {
        private val DEFAULT_NAME = "Vasily"
        private val DEFAULT_TRANSPORT = Transport(CAR)
        private val DEFAULT_LOCATION = INITIAL_LOCATION
        private val CENTER_LOCATION = Location(5,5)
        private val NEARER_LOCATION = INITIAL_LOCATION
        private val FARTHER_LOCATION = Location(10,10)
        private val DEFAULT_WEIGHT = Weight(1)
    }
}
