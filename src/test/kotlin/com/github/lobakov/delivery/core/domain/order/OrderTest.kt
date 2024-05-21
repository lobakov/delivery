package com.github.lobakov.delivery.core.domain.order

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.courier.Transport
import com.github.lobakov.delivery.core.domain.courier.TransportType.BYCICLE
import com.github.lobakov.delivery.core.domain.courier.TransportType.CAR
import com.github.lobakov.delivery.core.domain.order.OrderStatus.ASSIGNED
import com.github.lobakov.delivery.core.domain.order.OrderStatus.CREATED
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class OrderTest {

    @Test
    fun `should properly initialize order on creation`() {
        val uuid = UUID.randomUUID()

        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)

        assertEquals(uuid, sut.id)
        assertEquals(DEFAULT_LOCATION, sut.deliverTo)
        assertEquals(DEFAULT_WEIGHT, sut.weight)
        assertEquals(CREATED, sut.status)
        assertEquals(null, sut.courierId)
    }

    @Test
    fun `should properly assign courier`() {
        val uuid = UUID.randomUUID()
        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)

        assertEquals(CREATED, sut.status)
        assertEquals(null, sut.courierId)

        val courier = Courier("Vasily", Transport(CAR))
        sut.assign(courier)

        assertEquals(ASSIGNED, sut.status)
        assertEquals(courier.id, sut.courierId)
    }

    @Test
    fun `should properly close order`() {
        val uuid = UUID.randomUUID()

        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)

        val courier = Courier("Vasily", Transport(CAR))
        sut.assign(courier)

        assertEquals(ASSIGNED, sut.status)
        assertEquals(courier.id, sut.courierId)

        sut.close()
        assertEquals(OrderStatus.COMPLETED, sut.status)
    }

    @Test
    fun `should throw exception if courier has already been assigned`() {
        val uuid = UUID.randomUUID()
        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)
        val legitCourier = Courier("Vasily", Transport(CAR))

        sut.assign(legitCourier)

        assertEquals(ASSIGNED, sut.status)
        assertEquals(legitCourier.id, sut.courierId)

        val illegalCourier = Courier("Vitaly", Transport(BYCICLE))
        val expectedExceptionMessage = "Courier ${legitCourier.id} has been already assigned"

        val actualException = assertThrows<IllegalArgumentException> { sut.assign(illegalCourier) }
        assertEquals(expectedExceptionMessage, actualException.message)
    }

    @Test
    fun `should throw exception when assigning courier if order is closed`() {
        val uuid = UUID.randomUUID()
        val sut = Order(uuid, DEFAULT_LOCATION, DEFAULT_WEIGHT)

        val legitCourier = Courier("Vasily", Transport(CAR))
        sut.assign(legitCourier)

        assertEquals(ASSIGNED, sut.status)
        assertEquals(legitCourier.id, sut.courierId)

        val illegalCourier = Courier("Vitaly", Transport(BYCICLE))
        val expectedExceptionMessage = "Cannot assign courier to order with status = ${sut.status}"
        sut.close()

        val actualException = assertThrows<IllegalArgumentException> { sut.assign(illegalCourier) }
        assertEquals(expectedExceptionMessage, actualException.message)
    }

    companion object {
        private val DEFAULT_LOCATION = Location(1,1)
        private val DEFAULT_WEIGHT = Weight(1)
    }
}
