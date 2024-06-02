package com.github.lobakov.delivery.api.adapters.cron

import com.github.lobakov.delivery.api.adapters.cron.config.SchedulingConfig
import com.github.lobakov.delivery.api.adapters.mediator.CourierMediator
import com.github.lobakov.delivery.api.adapters.mediator.OrderMediator
import com.github.lobakov.delivery.core.application.command.assign.order.AssignOrderCommand
import com.github.lobakov.delivery.core.application.command.move.courier.MoveCourierCommand
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service

@Service
class ScheduledTasks(
    private val config: SchedulingConfig,
    private val scheduler: TaskScheduler,
    private val courierMediator: CourierMediator,
    private val orderMediator: OrderMediator
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
