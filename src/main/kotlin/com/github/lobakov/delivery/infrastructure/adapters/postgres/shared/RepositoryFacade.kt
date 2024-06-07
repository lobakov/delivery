package com.github.lobakov.delivery.infrastructure.adapters.postgres.shared

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RepositoryFacade(
    private val orderRepository: OrderRepository,
    private val courierRepository: CourierRepository
) {

    fun getCourierById(courierId: UUID): Courier? {
        return courierRepository.findById(courierId)
    }

    fun getOrderById(orderId: UUID): Order? {
        return orderRepository.findById(orderId)
    }

    fun findOrderByCourierIdAndStatus(courierId: UUID, status: OrderStatus): Order? {
        return orderRepository.findByCourierIdAndStatus(courierId, OrderStatus.CREATED)
    }

    fun addCourier(courier: Courier) {
        courierRepository.add(courier)
    }

    fun addOrder(order: Order) {
        orderRepository.add(order)
    }

    fun updateCourier(courier: Courier) {
        courierRepository.update(courier)
    }

    fun updateOrder(order: Order) {
        orderRepository.update(order)
    }

    fun getAllAssignedOrders(): List<Order> {
        return orderRepository.getAllAssigned()
    }

    fun getAllNotAssignedOrders(): List<Order> {
        return orderRepository.getAllNotAssigned()
    }

    fun getAllBusyCouriers(): List<Courier> {
        return courierRepository.getAllBusy()
    }

    fun getAllReadyCouriers(): List<Courier> {
        return courierRepository.getAllReady()
    }

    @Transactional
    fun updateCourierAndOrder(courier: Courier, order: Order) {
        courierRepository.update(courier)
        orderRepository.update(order)
    }
}
