package com.github.lobakov.delivery.core.domain.order

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.courier.Transport.BYCICLE
import com.github.lobakov.delivery.core.domain.courier.Transport.CAR
import com.github.lobakov.delivery.core.domain.order.OrderStatus.ASSIGNED
import com.github.lobakov.delivery.core.domain.order.OrderStatus.CREATED
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class OrderTest {

    @Test
    fun `order should be initialized with CREATED status and default location and weight on creation`() {
        //Given
        val uuid = UUID.randomUUID()

        //When
        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)

        //Then
        assertAll(
            "Ensure that order was properly initialized",
            { assertEquals(uuid, sut.id) },
            { assertEquals(DEFAULT_LOCATION, sut.deliverTo) },
            { assertEquals(DEFAULT_WEIGHT, sut.weight) },
            { assertEquals(CREATED, sut.status) },
            { assertEquals(null, sut.courierId) }
        )
    }

    @Test
    fun `order should transition to ASSIGNED status and receive courier id on assigning courier`() {
        //Given
        val uuid = UUID.randomUUID()
        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)

        //When
        val courier = Courier("Vasily", CAR)
        sut.assign(courier.id!!)

        //Then
        assertAll(
            "Ensure order status is ASSIGNED and courier id set",
            { assertEquals(ASSIGNED, sut.status) },
            { assertEquals(courier.id, sut.courierId) }
        )
    }

    @Test
    fun `order should transition to COMPLETED status on completing order`() {
        //Given
        val uuid = UUID.randomUUID()
        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)

        val courier = Courier("Vasily", CAR)
        sut.assign(courier.id!!)

        //When
        sut.complete()

        //Then
        assertEquals(OrderStatus.COMPLETED, sut.status)
    }

    @Test
    fun `order should throw exception on courier assignment if a courier has already been assigned to it`() {
        //Given
        val uuid = UUID.randomUUID()
        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)

        sut.assign(
            Courier("Vasily", CAR).id!!
        )

        //When, Then
        val actualException = assertThrows<IllegalArgumentException> {
            sut.assign(
                Courier("Vitaly", BYCICLE).id!!
            )
        }
        assertEquals(
            "Cannot assign courier to order with status = ${sut.status}",
            actualException.message
        )
    }

    @Test
    fun `order should throw exception when assigning courier if order is closed`() {
        //Given
        val uuid = UUID.randomUUID()
        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)

        sut.assign(
            Courier("Vasily", CAR).id!!
        )
        sut.complete()

        //When, Then
        val actualException = assertThrows<IllegalArgumentException> {
            sut.assign(
                Courier("Vitaly", BYCICLE).id!!
            )
        }
        assertEquals(
            "Cannot assign courier to order with status = ${sut.status}",
            actualException.message
        )
    }

    companion object {
        private val DEFAULT_LOCATION = Location(1, 1)
        private val DEFAULT_WEIGHT = Weight(1)
    }
}
