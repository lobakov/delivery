package com.github.lobakov.delivery.infrastructure.adapters.postgres.order

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.courier.Transport.BYCICLE
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD
import java.util.*

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class OrderRepositoryTest {

    @Autowired
    private lateinit var repository: OrderRepository

    @Test
    fun shouldSaveOrderEntityWhenAdded() {
        val sut = Order(
            id = UUID.randomUUID(),
            deliverTo = Location(10, 10),
            weight = Weight(1)
        )

        val actual = repository.add(sut)

        assertAll(
            "Ensure actual saved properties equal to aggregate",
            { assertEquals(sut.id, actual.id) },
            { assertEquals(sut.courierId, actual.courierId) },
            { assertEquals(sut.deliverTo, actual.deliverTo) },
            { assertEquals(sut.status, actual.status) },
            { assertEquals(sut.weight, actual.weight) }
        )
    }

    @Test
    fun shouldUpdateOrderEntityWhenUpdated() {
        //Given
        val sut = Order(
            id = UUID.randomUUID(),
            deliverTo = Location(10, 10),
            weight = Weight(1)
        )

        repository.add(sut)

        sut.assign(
            Courier("TestCourier", BYCICLE).id!!
        )

        //When
        repository.update(sut)
        val actual = repository.findById(sut.id)

        //Then
        assertAll(
            "Ensure actual updated properties equal to aggregate",
            { assertEquals(sut.id, actual?.id) },
            { assertEquals(sut.courierId, actual?.courierId) },
            { assertEquals(sut.deliverTo, actual?.deliverTo) },
            { assertEquals(sut.status, actual?.status) },
            { assertEquals(sut.weight, actual?.weight) }
        )
    }

    @Test
    fun shouldReturnAllOrdersInAssignedStatusWhenGetAllAssignedInvoked() {
        //Given
        val order1 = Order(
            id = UUID.randomUUID(),
            deliverTo = Location(10, 10),
            weight = Weight(1)
        )

        val order2 = Order(
            id = UUID.randomUUID(),
            deliverTo = Location(9, 9),
            weight = Weight(1)
        )

        order1.assign(
            Courier("TestCourier", BYCICLE).id!!
        )

        repository.add(order1)
        repository.add(order2)

        //When
        val actual: List<Order> = repository.getAllAssigned()

        //Then
        val expected = listOf(order1)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnAllOrdersInCreatedStatusWhenGetAllNotAssignedInvoked() {
        //Given
        val order1 = Order(
            id = UUID.randomUUID(),
            deliverTo = Location(10, 10),
            weight = Weight(1)
        )

        val order2 = Order(
            id = UUID.randomUUID(),
            deliverTo = Location(9, 9),
            weight = Weight(1)
        )

        order1.assign(
            Courier("TestCourier", BYCICLE).id!!
        )

        repository.add(order1)
        repository.add(order2)

        //When
        val actual: List<Order> = repository.getAllNotAssigned()

        //Then
        val expected = listOf(order2)
        assertEquals(expected, actual)
    }
}
