package com.github.lobakov.delivery.api.adapters.http

import com.github.lobakov.delivery.api.adapters.http.contract.OrdersApi
import com.github.lobakov.delivery.api.adapters.http.model.Order
import com.github.lobakov.delivery.core.application.command.assign.order.AssignOrderCommand
import com.github.lobakov.delivery.core.application.command.assign.order.AssignOrderHandler
import com.github.lobakov.delivery.core.ports.order.OrderRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class OrderController(
    private val assignOrderHandler: AssignOrderHandler,
    private val createOrderHandler: AssignOrderHandler,
    private val repository: OrderRepository,
    private val mapper: OrderDtoMapper
) : OrdersApi {

    private val NOT_FOUND = ResponseEntity.notFound().build<Order>()

    override fun assignOrder(orderId: UUID?): ResponseEntity<Void> {
        assignOrderHandler.handle(
            AssignOrderCommand(orderId)
        )
        return ResponseEntity.ok().build()
    }

    override fun createOrder(): ResponseEntity<Void> {
        v

        return ResponseEntity.ok().build()
    }

    override fun getOrder(orderId: UUID?): ResponseEntity<Order> {
        val id = orderId ?: return NOT_FOUND
        val order = repository.findById(orderId) ?: return NOT_FOUND

        return ResponseEntity
            .ok()
            .body(mapp)
            .build()
    }
}