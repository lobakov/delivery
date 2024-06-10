package com.github.lobakov.delivery.core.application.usecase.command.create.order

import com.github.lobakov.delivery.core.application.usecase.shared.CommandHandler
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domain.order.OrderStatus
import com.github.lobakov.delivery.core.domain.sharedkernel.Location
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight
import com.github.lobakov.delivery.core.ports.geo.GeoService
import com.github.lobakov.delivery.core.ports.notifier.NotifierService
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.RepositoryFacade
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.coroutines.CoroutineContext

@Service
class CreateOrderHandler(
    private val ioContext: CoroutineContext,
    private val repositoryFacade: RepositoryFacade,
    private val geoService: GeoService,
    private val notifierService: NotifierService
) : CommandHandler<CreateOrderCommand> {

    override fun handle(command: CreateOrderCommand): Unit = runBlocking(ioContext) {
        val basketId = command.basketId

        if (basketId == null) {
            repositoryFacade.addOrder(
                Order(
                    id = UUID.randomUUID(),
                    deliverTo = Location.getRandomLocation(),
                    weight = Weight.getRandomWeight()
                )
            )
        } else {
            repositoryFacade.getOrderById(command.basketId) ?: createOrder(command)
        }
    }

    private suspend fun createOrder(command: CreateOrderCommand) {
        val orderId = command.basketId!!
        val location = run {
            geoService.getGeoLocation(command.address!!)
        }
        val weight = Weight(command.weight!!)

        repositoryFacade.addOrder(
            Order(orderId, location, weight)
        )

        notifierService.notify(orderId, OrderStatus.CREATED)
    }
}
