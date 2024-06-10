package com.github.lobakov.delivery.core.application.usecase.command.assign.order


import com.github.lobakov.delivery.core.application.usecase.shared.CommandHandler
import com.github.lobakov.delivery.core.domain.order.Order
import com.github.lobakov.delivery.core.domainservices.DispatchService
import com.github.lobakov.delivery.core.ports.notifier.NotifierService
import com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.RepositoryFacade
import org.springframework.stereotype.Service

@Service
class AssignOrderHandler(
    private val dispatcher: DispatchService,
    private val repositoryFacade: RepositoryFacade,
    private val notifierService: NotifierService
) : CommandHandler<AssignOrderCommand> {

    override fun handle(command: AssignOrderCommand) {
        val orderId = command.orderId

        if (orderId == null) {
            repositoryFacade.getAllNotAssignedOrders()
                .forEach {
                    assign(it)
                }
        } else {
            val order = repositoryFacade.getOrderById(orderId)
                ?: throw IllegalStateException("Order $orderId not found")
            assign(order)
        }
    }

    private fun assign(order: Order) {
        val readyCouriers = repositoryFacade.getAllReadyCouriers()

        val courier = dispatcher.dispatch(order, readyCouriers)
            ?: return

        courier.assign(order)

        repositoryFacade.updateCourierAndOrder(courier, order)
        notifierService.notify(order.id, order.status)
    }
}
