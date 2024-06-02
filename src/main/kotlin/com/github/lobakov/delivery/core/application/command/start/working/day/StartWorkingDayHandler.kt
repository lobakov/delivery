package com.github.lobakov.delivery.core.application.command.start.working.day

import com.github.lobakov.delivery.core.application.shared.CommandHandler
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import org.springframework.stereotype.Service

@Service
class StartWorkingDayHandler(
    private val repository: CourierRepository
) : CommandHandler<StartWorkingDayCommand> {

    override fun handle(command: StartWorkingDayCommand) {
        val id = command.courierId

        val courier = repository.findById(id)
            ?: throw IllegalStateException("No courier with id $id found")

        courier.startWorkingDay()
        repository.update(courier)
    }
}
