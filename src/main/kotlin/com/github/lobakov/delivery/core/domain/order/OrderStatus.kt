package com.github.lobakov.delivery.core.domain.order

enum class OrderStatus(val value: String) {
    CREATED("CREATED"),
    ASSIGNED("ASSIGNED"),
    COMPLETED("COMPLETED");

    companion object {
        private val statusMap = entries.associateBy { it.value }
        operator fun get(value: String) = statusMap[value]
    }
}
