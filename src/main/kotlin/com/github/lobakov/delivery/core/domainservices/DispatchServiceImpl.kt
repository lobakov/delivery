package com.github.lobakov.delivery.core.domainservices

import com.github.lobakov.delivery.core.domain.courier.Courier
import com.github.lobakov.delivery.core.domain.courier.CourierStatus.READY
import com.github.lobakov.delivery.core.domain.order.Order
import org.springframework.stereotype.Service

@Service
class DispatchServiceImpl : DispatchService {

    override fun dispatch(order: Order, couriers: List<Courier>): Courier? {
        return couriers
            .filter { it.status == READY }
            .filter { it.transport.canCarry(order.weight) }
            .minByOrNull { it.countStepsTo(order.deliverTo) }
    }
}
