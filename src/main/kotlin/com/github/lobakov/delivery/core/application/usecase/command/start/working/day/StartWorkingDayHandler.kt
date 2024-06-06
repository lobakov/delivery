package com.github.lobakov.delivery.core.application.usecase.command.start.working.day

import com.github.lobakov.delivery.api.adapters.http.exception.CourierNotFoundException
import com.github.lobakov.delivery.api.adapters.http.exception.InvalidCourierStateException
import com.github.lobakov.delivery.core.application.usecase.shared.CommandHandler
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import org.springframework.stereotype.Service

@Service
class StartWorkingDayHandler(
    private val repository: CourierRepository
) : CommandHandler<StartWorkingDayCommand> {

    override fun handle(command: StartWorkingDayCommand) {
        val id = command.courierId

        val courier = repository.findById(id)
            ?: throw CourierNotFoundException("Courier with id = $id not found")

        try {
            courier.startWorkingDay()
        } catch (e: IllegalArgumentException) {
            throw InvalidCourierStateException(e.localizedMessage)
        }

        repository.update(courier)
    }
}
