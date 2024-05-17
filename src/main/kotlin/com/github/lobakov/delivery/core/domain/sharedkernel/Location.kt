package com.github.lobakov.delivery.core.domain.sharedkernel

import kotlin.math.abs

data class Location(
    val x: Int,
    val y: Int
) {

    init {
        require(X_RANGE.contains(x) && Y_RANGE.contains(y)) { "Location coordinates are out of valid range. Should be in range [1..10]" }
    }

    fun distanceTo(target: Location): Int = abs((x + y) - (target.x + target.y))

    companion object {
        private const val MIN_X = 1
        private const val MIN_Y = 1
        private const val MAX_X = 10
        private const val MAX_Y = 10
        private val X_RANGE = MIN_X..MAX_X
        private val Y_RANGE = MIN_Y..MAX_Y
    }
}
