package com.github.lobakov.delivery.core.domainservices

import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import java.util.*

@SpringBootTest
class DispatcherServiceTest {

    @Autowired
    private lateinit var repository: CourierRepository

    @Autowired
    private lateinit var dispatchService: DispatchService

    @Test
    @Sql(
        statements = [
            "INSERT INTO delivery.t_courier(id, version, name, status, current_location, transport) " +
                    "VALUES('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 1, 'Petya', 'READY', '5, 5', 1), " +
                    "('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 1, 'Vanya', 'READY', '1, 1', 4), " +
                    "('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 1, 'Sasha', 'BUSY',  '9, 9', 3), " +
                    "('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4', 1, 'Igor', 'READY',  '9, 9', 1), " +
                    "('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5', 1, 'Kolya', 'READY', '8, 8', 3);"
        ]
    )
    fun `should dispatch closest courier capable of transporting weight`() {
        //Given
        val order = Order(UUID.randomUUID(), Location(10, 10), Weight(5))
        val readyCouriers = repository.getAllReady()

        //When
        val actualCourier = dispatchService.dispatch(order, readyCouriers)

        //Then
        val expectedId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5")
        val expectedCourier = readyCouriers.find { it.id == expectedId }

        assertAll(
            "Ensure proper courier was assigned",
            { assertEquals(expectedCourier?.id, actualCourier?.id) },
            { assertEquals(expectedCourier?.name, actualCourier?.name) },
            { assertEquals(expectedCourier?.status, actualCourier?.status) },
            { assertEquals(expectedCourier?.currentLocation, actualCourier?.currentLocation) },
            { assertEquals(expectedCourier?.transport, actualCourier?.transport) }
        )
    }

}