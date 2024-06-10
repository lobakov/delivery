package com.github.lobakov.delivery.core.ports.notifier

import com.github.lobakov.delivery.core.domain.order.OrderStatus
import java.util.UUID

fun interface NotifierService {

    fun notify(orderId: UUID, newStatus: OrderStatus)
}
