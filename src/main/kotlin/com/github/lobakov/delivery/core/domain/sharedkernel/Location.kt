package com.github.lobakov.delivery.core.domain.sharedkernel

import kotlin.math.abs

data class Location(
    val x: Int,
    val y: Int
) {

    init {
        require(X_RANGE.contains(x) && Y_RANGE.contains(y)) {
            """
               Location coordinates are out of valid range.
               X Should be in range [$MIN_X..$MAX_X]
               Y Should be in range [$MIN_Y..$MAX_Y]
            """
        }
    }

    fun distanceTo(target: Location): Int = abs((x + y) - (target.x + target.y))

    companion object {
        const val MIN_X = 1
        const val MIN_Y = 1
        const val MAX_X = 10
        const val MAX_Y = 10
        val X_RANGE = MIN_X..MAX_X
        val Y_RANGE = MIN_Y..MAX_Y
    }
}
