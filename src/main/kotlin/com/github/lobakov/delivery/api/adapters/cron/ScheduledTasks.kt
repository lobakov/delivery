package com.github.lobakov.delivery.api.adapters.cron

import com.github.lobakov.delivery.api.adapters.cron.config.SchedulingConfig
import com.github.lobakov.delivery.api.adapters.mediator.CourierMediator
import com.github.lobakov.delivery.api.adapters.mediator.OrderMediator
import com.github.lobakov.delivery.core.application.usecase.command.assign.order.AssignOrderCommand
import com.github.lobakov.delivery.core.application.usecase.command.move.courier.MoveCourierCommand
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service

@Service
class ScheduledTasks(
    private val courierMediator: CourierMediator,
    private val orderMediator: OrderMediator,
    config: SchedulingConfig,
    scheduler: TaskScheduler,
) {

    private val assignOrdersTask = Runnable {
        orderMediator.mediate(AssignOrderCommand())
    }

    private val moveCouriersTask = Runnable {
        courierMediator.mediate(MoveCourierCommand())
    }

    init {
        scheduler.schedule(assignOrdersTask, CronTrigger(config.assignOrders))
        scheduler.schedule(moveCouriersTask, CronTrigger(config.moveCouriers))
    }
}
