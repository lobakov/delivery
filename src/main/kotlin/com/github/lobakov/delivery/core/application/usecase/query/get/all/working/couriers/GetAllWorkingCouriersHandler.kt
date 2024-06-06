package com.github.lobakov.delivery.core.application.usecase.query.get.all.working.couriers

import com.github.lobakov.delivery.core.application.usecase.shared.QueryHandler
import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.ports.courier.CourierRepository
import org.springframework.stereotype.Service

@Service
class GetAllWorkingCouriersHandler(
    private val repository: CourierRepository
) : QueryHandler<GetAllWorkingCouriersCommand, List<Courier>> {

    override fun handle(command: GetAllWorkingCouriersCommand): List<Courier> {
        val busyCouriers = repository.getAllBusy()
        val readyCouriers = repository.getAllReady()

        return busyCouriers + readyCouriers
    }
}
