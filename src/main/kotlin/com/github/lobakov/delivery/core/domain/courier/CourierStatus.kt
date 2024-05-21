package com.github.lobakov.delivery.core.domain.courier

enum class CourierStatus(val value: String) {
    NOT_AVAILABLE("NOT AVAILABLE"),
    READY("READY"),
    BUSY("BUSY");

    companion object {
        private val statusMap = entries.associateBy { it.value }
        operator fun get(value: String) = statusMap[value]
    }
}
