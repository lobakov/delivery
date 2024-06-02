package com.github.lobakov.delivery.api.adapters.http

import com.github.lobakov.delivery.api.adapters.http.contract.CouriersApi
import com.github.lobakov.delivery.core.application.command.finish.working.day.FinishWorkingDayHandler
import com.github.lobakov.delivery.core.application.command.move.courier.MoveCourierHandler
import com.github.lobakov.delivery.core.application.command.start.working.day.StartWorkingDayHandler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import java.util.*

@RestController
class CourierController(
    private val startWorkingDayHandler: StartWorkingDayHandler,
    private val finishWorkingDayHandler: FinishWorkingDayHandler,
    private val moveCourierHandler: MoveCourierHandler
): CouriersApi {

    override fun finishWorkingDay(courierId: UUID?): ResponseEntity<Void> {
        return super.finishWorkingDay(courierId)
    }

    override fun moveToLocation(courierId: UUID?): ResponseEntity<Void> {
        return super.moveToLocation(courierId)
    }

    override fun startWorkingDay(courierId: UUID?): ResponseEntity<Void> {
        return super.startWorkingDay(courierId)
    }
}