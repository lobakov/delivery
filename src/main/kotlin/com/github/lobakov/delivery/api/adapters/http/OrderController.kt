package com.github.lobakov.delivery.api.adapters.http

import com.github.lobakov.delivery.api.adapters.http.contract.OrdersApi
import com.github.lobakov.delivery.api.adapters.http.model.Location
import com.github.lobakov.delivery.api.adapters.http.model.Order
import com.github.lobakov.delivery.core.application.usecase.command.assign.order.AssignOrderCommand
import com.github.lobakov.delivery.core.application.usecase.command.assign.order.AssignOrderHandler
import com.github.lobakov.delivery.core.application.usecase.command.create.order.CreateOrderCommand
import com.github.lobakov.delivery.core.application.usecase.command.create.order.CreateOrderHandler
import com.github.lobakov.delivery.core.application.usecase.query.get.order.GetOrderCommand
import com.github.lobakov.delivery.core.application.usecase.query.get.order.GetOrderHandler
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class OrderController(
    private val assignOrderHandler: AssignOrderHandler,
    private val createOrderHandler: CreateOrderHandler,
    private val getOrderHandler: GetOrderHandler
) : OrdersApi {

    override fun assignOrder(orderId: UUID): ResponseEntity<Unit> {
        assignOrderHandler.handle(AssignOrderCommand(orderId))
        return SUCCESS_RESPONSE
    }

    override fun createOrder(): ResponseEntity<Unit> {
        createOrderHandler.handle(CreateOrderCommand())
        return CREATED_RESPONSE
    }

    override fun getOrder(orderId: UUID): ResponseEntity<Order> {
        val orderDto = getOrderHandler.handle(GetOrderCommand(orderId))

        return ResponseEntity.ok()
            .body(
                Order(
                    id = orderDto.id,
                    courierId = orderDto.courierId,
                    courierLocation = orderDto.courierLocation?.toDto()
                )
            )
    }

    private fun com.github.lobakov.delivery.core.domain.sharedkernel.Location.toDto(): Location =
        Location(this.x, this.y)

    companion object {
        private val CREATED_RESPONSE = ResponseEntity.status(HttpStatus.CREATED).build<Unit>()
        private val SUCCESS_RESPONSE = ResponseEntity.ok().build<Unit>()
    }
}
