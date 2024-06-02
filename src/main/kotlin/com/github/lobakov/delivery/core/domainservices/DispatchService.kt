package com.github.lobakov.delivery.core.domainservices

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.order.Order

fun interface DispatchService {

    fun dispatch(order: Order, couriers: List<Courier>): Courier?
}
