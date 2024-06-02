package com.github.lobakov.delivery.api.adapters.mediator

import com.github.lobakov.delivery.core.application.command.move.courier.MoveCourierCommand
import com.github.lobakov.delivery.core.application.command.move.courier.MoveCourierHandler
import org.springframework.stereotype.Component

@Component
class CourierMediator(
    private val moveCourierHandler: MoveCourierHandler
): Mediator<MoveCourierCommand?> {

    override fun mediate(command: MoveCourierCommand?) {
        val moveCourierCommand = command ?: MoveCourierCommand()
        moveCourierHandler.handle(moveCourierCommand)
    }
}
