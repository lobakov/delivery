package com.github.lobakov.delivery.api.adapters.mediator

import com.github.lobakov.delivery.core.application.usecase.command.assign.order.AssignOrderCommand
import com.github.lobakov.delivery.core.application.usecase.command.assign.order.AssignOrderHandler
import org.springframework.stereotype.Component

@Component
class OrderMediator(
    private val assignOrderHandler: AssignOrderHandler
): Mediator<AssignOrderCommand> {

    override fun mediate(command: AssignOrderCommand) {
        assignOrderHandler.handle(command)
    }
}
