package com.github.lobakov.delivery.infrastructure.adapters.postgres.courier

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.courier.Transport.BYCICLE
import com.github.lobakov.delivery.core.domain.courier.Transport.CAR
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD
import org.springframework.test.context.jdbc.Sql
import java.util.*

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class CourierRepositoryTest {

    @Autowired
    private lateinit var repository: CourierRepository

    @Test
    fun shouldSaveCourierEntityWhenAdded() {
        val sut = Courier("TestCourier", BYCICLE)

        val actual = repository.add(sut)

        assertAll(
            "Ensure actual saved properties equal to aggregate",
            { assertEquals(sut.id, actual.id) },
            { assertEquals(sut.name, actual.name) },
            { assertEquals(sut.transport, actual.transport) },
            { assertEquals(sut.status, actual.status) },
            { assertEquals(sut.currentLocation, actual.currentLocation) }
        )
    }

    @Test
    @Sql(
        statements = [
            "INSERT INTO delivery.t_courier(id, version, name, status, current_location, transport) " +
                    "VALUES('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 1, 'Ignat', 'READY', '10, 10', 3);"
        ]
    )
    fun shouldUpdateCourierEntityWhenUpdated() {
        //Given
        val sut = repository.findById(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2"))

        //When
        sut.assign(
            Order(
                id = UUID.randomUUID(),
                deliverTo = Location(10, 10),
                weight = Weight(1)
            )
        )
        repository.update(sut)
        val actual = repository.findById(sut.id!!)

        //Then
        assertAll(
            "Ensure actual updated properties equal to aggregate",
            { assertEquals(sut.id, actual.id) },
            { assertEquals(sut.name, actual.name) },
            { assertEquals(sut.transport, actual.transport) },
            { assertEquals(sut.status, actual.status) },
            { assertEquals(sut.currentLocation, actual.currentLocation) }
        )
    }

    @Test
    @Sql(
        statements = [
            "INSERT INTO delivery.t_courier(id, version, name, status, current_location, transport) " +
                    "VALUES('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 1, 'Ignat', 'READY', '1, 1', 3);"
        ]
    )
    fun shouldReturnAllCouriersInReadyStatusWhenGetAllReadyInvoked() {
        //When
        val actual: List<Courier> = repository.getAllReady()

        //Then
        val expected = listOf(
            Courier(
                "Ignat",
                BYCICLE,
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1")
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    @Sql(
        statements = [
            "INSERT INTO delivery.t_courier(id, version, name, status, current_location, transport) " +
                    "VALUES('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa0', 1, 'Ignat', 'BUSY', '1, 1', 3);"
        ]
    )
    fun shouldReturnAllCouriersInBusyStatusWhenGetAllBusyInvoked() {
        //When
        val actual: List<Courier> = repository.getAllBusy()

        //Then
        val expected = listOf(
            Courier(
                "Ignat",
                BYCICLE,
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa0")
            )
        )
        assertEquals(expected, actual)
    }
}
