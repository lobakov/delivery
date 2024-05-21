package com.github.lobakov.delivery.core.domain.courier

import com.github.lobakov.delivery.core.domain.courier.CourierStatus.*
import com.github.lobakov.delivery.core.domain.courier.TransportType.CAR
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus.*
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class CourierTest {

    @Test
    fun `should properly initialize courier on creation`() {
        val sut = DEFAULT_COURIER

        assertEquals(DEFAULT_NAME, sut.name)
        assertEquals(DEFAULT_TRANSPORT, sut.transport)
        assertEquals(NOT_AVAILABLE, sut.status)
        assertEquals(DEFAULT_LOCATION, sut.currentLocation)
    }

    @Test
    fun `should properly start working day`() {
        val sut = DEFAULT_COURIER

        assertEquals(NOT_AVAILABLE, sut.status)

        sut.startWorkingDay()

        assertEquals(READY, sut.status)
    }

    @Test
    fun `should throw exception on starting working day when day already started`() {
        val sut = DEFAULT_COURIER

        assertEquals(NOT_AVAILABLE, sut.status)

        sut.startWorkingDay()

        assertEquals(READY, sut.status)

        val expectedExceptionMessage = "Working day has already started"

        val actualException = assertThrows<IllegalArgumentException> { sut.startWorkingDay() }
        assertEquals(expectedExceptionMessage, actualException.message)
    }

    @Test
    fun `should throw exception on finishing working day when day has not yet started`() {
        val sut = DEFAULT_COURIER

        assertEquals(NOT_AVAILABLE, sut.status)

        val expectedExceptionMessage = "Cannot finish the working day, the courier is in ${sut.status} status"

        val actualException = assertThrows<IllegalArgumentException> { sut.finishWorkingDay() }
        assertEquals(expectedExceptionMessage, actualException.message)
    }

    @Test
    fun `should properly assign order`() {
        val sutCourier = DEFAULT_COURIER
        val sutOrder = Order(UUID.randomUUID(), DEFAULT_LOCATION, DEFAULT_WEIGHT)

        assertEquals(NOT_AVAILABLE, sutCourier.status)

        sutCourier.startWorkingDay()

        assertEquals(READY, sutCourier.status)
        assertEquals(CREATED, sutOrder.status)

        sutCourier.assign(sutOrder)

        assertEquals(BUSY, sutCourier.status)
        assertEquals(ASSIGNED, sutOrder.status)
        assertEquals(sutCourier.id, sutOrder.courierId)
    }

    @Test
    fun `should properly close order`() {
        val sut = DEFAULT_COURIER
        val order = Order(UUID.randomUUID(), DEFAULT_LOCATION, DEFAULT_WEIGHT)

        assertEquals(NOT_AVAILABLE, sut.status)

        sut.startWorkingDay()

        assertEquals(READY, sut.status)
        assertEquals(CREATED, order.status)

        sut.assign(order)

        assertEquals(BUSY, sut.status)
        assertEquals(ASSIGNED, order.status)

        sut.close(order)

        assertEquals(READY, sut.status)
        assertEquals(COMPLETED, order.status)
    }

    @Test
    fun `should throw exception on finishing working day when delivering goods`() {
        val sut = DEFAULT_COURIER
        val order = Order(UUID.randomUUID(), DEFAULT_LOCATION, DEFAULT_WEIGHT)

        assertEquals(NOT_AVAILABLE, sut.status)

        sut.startWorkingDay()

        assertEquals(READY, sut.status)

        sut.assign(order)

        val expectedExceptionMessage = "Cannot finish the working day, the courier is in ${sut.status} status"

        val actualException = assertThrows<IllegalArgumentException> { sut.finishWorkingDay() }
        assertEquals(expectedExceptionMessage, actualException.message)
    }

    @Test
    fun `should properly determine step count`() {
        val sut = DEFAULT_COURIER
        val order = Order(UUID.randomUUID(), CENTER_LOCATION, DEFAULT_WEIGHT)

        sut.startWorkingDay()
        sut.assign(order)

        val expectedStepCount = 2
        assertEquals(expectedStepCount, sut.getStepCount(order.deliverTo))
    }

    @Test
    fun `should properly move down and forward`() {
        val sut = DEFAULT_COURIER
        val order = Order(UUID.randomUUID(), CENTER_LOCATION, DEFAULT_WEIGHT)

        sut.startWorkingDay()
        sut.assign(order)

        val expectedStepCount = 2
        assertEquals(expectedStepCount, sut.getStepCount(order.deliverTo))
    }

    @Test
    fun `should properly move up and backwards`() {
        val sut = DEFAULT_COURIER
        sut.currentLocation = CENTER_LOCATION
        val order = Order(UUID.randomUUID(), NEARER_LOCATION, DEFAULT_WEIGHT)
        val destination = order.deliverTo

        sut.startWorkingDay()
        sut.assign(order)

        val expectedStepCount = 2
        assertEquals(expectedStepCount, sut.getStepCount(destination))

        val expectedIntermediateLocation = Location(1, 5)
        sut.moveTo(destination)

        assertEquals(expectedIntermediateLocation, sut.currentLocation)

        sut.moveTo(destination)

        assertEquals(DEFAULT_LOCATION, sut.currentLocation)
        assertEquals(true, sut.hasReached(destination))
    }

    @Test
    fun `should properly determine when destination has been reached`() {
        val sut = DEFAULT_COURIER
        sut.currentLocation = CENTER_LOCATION
        val order = Order(UUID.randomUUID(), FARTHER_LOCATION, DEFAULT_WEIGHT)
        val destination = order.deliverTo

        sut.startWorkingDay()
        sut.assign(order)

        val expectedStepCount = 3
        assertEquals(expectedStepCount, sut.getStepCount(destination))

        val expectedFirstIntermediateLocation = Location(9, 5)
        sut.moveTo(destination)

        assertEquals(expectedFirstIntermediateLocation, sut.currentLocation)

        val expectedSecondIntermediateLocation = Location(10, 8)
        sut.moveTo(destination)

        assertEquals(expectedSecondIntermediateLocation, sut.currentLocation)

        sut.moveTo(destination)

        assertEquals(destination, sut.currentLocation)
        assertEquals(true, sut.hasReached(destination))
    }

    companion object {
        private val DEFAULT_NAME = "Vasily"
        private val DEFAULT_TRANSPORT = Transport(CAR)
        private val DEFAULT_COURIER = Courier(DEFAULT_NAME, DEFAULT_TRANSPORT)
        private val DEFAULT_LOCATION = Location(1,1)
        private val CENTER_LOCATION = Location(5,5)
        private val NEARER_LOCATION = DEFAULT_LOCATION
        private val FARTHER_LOCATION = Location(10,10)
        private val DEFAULT_WEIGHT = Weight(1)
    }
}