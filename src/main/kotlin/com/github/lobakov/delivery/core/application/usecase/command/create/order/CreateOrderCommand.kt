package com.github.lobakov.delivery.core.application.usecase.command.create.order

import com.github.lobakov.delivery.api.adapters.http.exception.CreateOrderException
import java.util.*

class CreateOrderCommand(val basketId: UUID? = null, val address: String? = null, val weight: Int? = null) {

    init {
        if (!allNullOrAllNotNull()) {
            val message = "On creating order all fields should be either empty or not empty: " +
                    "basketId = $basketId, address = $address, weight = $weight"
            throw CreateOrderException(message)
        }
    }

    private fun allNullOrAllNotNull() =
        (basketId == null && address.isNullOrBlank() && weight == null)
                || (basketId != null && !address.isNullOrBlank() && weight != null)
}
