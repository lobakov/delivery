package com.github.lobakov.delivery.core.domain.order

enum class OrderStatus(val id: Int, val value: String) {
    CREATED(1, "CREATED"),
    ASSIGNED(2, "ASSIGNED"),
    COMPLETED(3, "COMPLETED");

    companion object {
        private val statusMap = entries.associateBy { it.value }
        operator fun get(value: String) = statusMap[value]
        operator fun get(id: Int) = entries[id]
    }
}
