package com.github.lobakov.delivery.core.application.usecase.command.finish.working.day

import com.github.lobakov.delivery.api.adapters.http.exception.CourierNotFoundException
import com.github.lobakov.delivery.api.adapters.http.exception.InvalidCourierStateException
import com.github.lobakov.delivery.core.application.usecase.shared.CommandHandler
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.RepositoryFacade
import org.springframework.stereotype.Service

@Service
class FinishWorkingDayHandler(
    private val repositoryFacade: RepositoryFacade
) : CommandHandler<FinishWorkingDayCommand> {

    override fun handle(command: FinishWorkingDayCommand) {
        val id = command.courierId

        val courier = repositoryFacade.getCourierById(id)
            ?: throw CourierNotFoundException("Courier with id = $id not found")

        try {
            courier.finishWorkingDay()
        } catch (e: IllegalArgumentException) {
            throw InvalidCourierStateException(e.localizedMessage)
        }

        repositoryFacade.updateCourier(courier)
    }
}
