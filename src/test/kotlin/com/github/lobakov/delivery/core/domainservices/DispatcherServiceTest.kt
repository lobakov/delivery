package com.github.lobakov.delivery.core.domainservices

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.courier.Transport.*
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class DispatcherServiceTest {

    @Autowired
    private lateinit var dispatchService: DispatchService

    private lateinit var readyCouriers: List<Courier>

    final val sasha = Courier(
        "Sasha",
        CAR,
        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1")
    )

    final val igor = Courier(
        "Igor",
        PEDESTRIAN,
        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2")
    )

    final val samBridges = Courier(
        "Sam Bridges",
        SCOOTER,
        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3")
    )

    init {
        sasha.currentLocation = Location(1, 1)
        igor.currentLocation = Location(9, 9)
        samBridges.currentLocation = Location(7, 7)

        readyCouriers = listOf(sasha, igor, samBridges)

        readyCouriers.forEach { it.startWorkingDay() }
    }


    @Test
    fun `should dispatch closest courier capable of transporting weight`() {
        //Given
        val order = Order(UUID.randomUUID(), Location(10, 10), Weight(5))

        //When
        val actualCourier = dispatchService.dispatch(order, readyCouriers)

        assertAll(
            "Ensure proper courier was assigned",
            { assertEquals(samBridges.id, actualCourier?.id) },
            { assertEquals(samBridges.name, actualCourier?.name) },
            { assertEquals(samBridges.status, actualCourier?.status) },
            { assertEquals(samBridges.currentLocation, actualCourier?.currentLocation) },
            { assertEquals(samBridges.transport, actualCourier?.transport) }
        )
    }
}
