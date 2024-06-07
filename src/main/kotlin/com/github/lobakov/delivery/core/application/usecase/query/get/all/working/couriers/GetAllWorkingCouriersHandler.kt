package com.github.lobakov.delivery.core.application.usecase.query.get.all.working.couriers

import com.github.lobakov.delivery.core.application.usecase.shared.QueryHandler
import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.RepositoryFacade
import org.springframework.stereotype.Service

@Service
class GetAllWorkingCouriersHandler(
    private val repositoryFacade: RepositoryFacade
) : QueryHandler<GetAllWorkingCouriersQuery, List<Courier>> {

    override fun handle(query: GetAllWorkingCouriersQuery): List<Courier> {
        val busyCouriers = repositoryFacade.getAllBusyCouriers()
        val readyCouriers = repositoryFacade.getAllReadyCouriers()

        return busyCouriers + readyCouriers
    }
}
