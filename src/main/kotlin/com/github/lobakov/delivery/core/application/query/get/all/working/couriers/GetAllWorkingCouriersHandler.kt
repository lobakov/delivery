package com.github.lobakov.delivery.core.application.query.get.all.working.couriers

import com.github.lobakov.delivery.core.application.shared.QueryHandler
import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.ports.courier.CourierRepository

class GetAllWorkingCouriersHandler(
    private val repository: CourierRepository
) : QueryHandler<GetAllWorkingCouriersCommand, List<Courier>> {

    override fun handle(command: GetAllWorkingCouriersCommand): List<Courier> {
        val busyCouriers = repository.getAllBusy()
        val readyCouriers = repository.getAllReady()

        return busyCouriers + readyCouriers
    }
}
