package com.github.lobakov.delivery.api.adapters.http


import com.github.lobakov.delivery.api.adapters.http.contract.CouriersApi
import com.github.lobakov.delivery.core.application.usecase.command.finish.working.day.FinishWorkingDayCommand
import com.github.lobakov.delivery.core.application.usecase.command.finish.working.day.FinishWorkingDayHandler
import com.github.lobakov.delivery.core.application.usecase.command.move.courier.MoveCourierCommand
import com.github.lobakov.delivery.core.application.usecase.command.move.courier.MoveCourierHandler
import com.github.lobakov.delivery.core.application.usecase.command.start.working.day.StartWorkingDayCommand
import com.github.lobakov.delivery.core.application.usecase.command.start.working.day.StartWorkingDayHandler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class CourierController(
    private val startWorkingDayHandler: StartWorkingDayHandler,
    private val finishWorkingDayHandler: FinishWorkingDayHandler,
    private val moveCourierHandler: MoveCourierHandler
): CouriersApi {

    override fun moveToLocation(courierId: UUID): ResponseEntity<Unit> {
        moveCourierHandler.handle(MoveCourierCommand(courierId))
        return SUCCESS_RESPONSE
    }

    override fun startWorkingDay(courierId: UUID): ResponseEntity<Unit> {
        startWorkingDayHandler.handle(StartWorkingDayCommand(courierId))
        return SUCCESS_RESPONSE
    }

    override fun finishWorkingDay(courierId: UUID): ResponseEntity<Unit> {
        finishWorkingDayHandler.handle(FinishWorkingDayCommand(courierId))
        return SUCCESS_RESPONSE
    }

    companion object {
        private val SUCCESS_RESPONSE = ResponseEntity.ok().build<Unit>()
    }
}
