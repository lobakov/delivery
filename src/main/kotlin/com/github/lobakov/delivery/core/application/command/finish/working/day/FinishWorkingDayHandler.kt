package com.github.lobakov.delivery.core.application.command.finish.working.day

import com.github.lobakov.delivery.core.application.shared.CommandHandler
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import org.springframework.stereotype.Service

@Service
class FinishWorkingDayHandler(
    private val repository: CourierRepository
) : CommandHandler<FinishWorkingDayCommand> {

    override fun handle(command: FinishWorkingDayCommand) {
        val id = command.courierId

        val courier = repository.findById(id)
            ?: throw IllegalStateException("No courier with id $id found")

        courier.finishWorkingDay()
        repository.update(courier)
    }
}
