package com.github.lobakov.delivery.core.domain.sharedkernel

import kotlin.math.abs
import kotlin.math.sign
import kotlin.random.Random
import kotlin.random.nextInt

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

    fun moveTo(destination: Location, transportSpeed: Int): Location {
        var newLocation = this.copy()

        val deltaX = destination.x - newLocation.x
        var numberOfStepsX = abs(deltaX)
        val directionX = deltaX.sign
        var speed = transportSpeed

        while (numberOfStepsX > 0 && speed > 0) {
            val newX = when (directionX) {
                LEFT -> newLocation.x - STEP
                RIGHT -> newLocation.x + STEP
                else -> break
            }

            newLocation = Location(newX, newLocation.y)
            numberOfStepsX -= STEP
            speed -= STEP
        }

        val deltaY = destination.y - newLocation.y
        var numberOfStepsY = abs(deltaY)
        val directionY: Int = deltaY.sign

        while (numberOfStepsY > 0 && speed > 0) {
            val newY = when (directionY) {
                UP -> newLocation.y - STEP
                DOWN -> newLocation.y + STEP
                else -> break
            }

            newLocation = Location(newLocation.x, newY)
            numberOfStepsY -= STEP
            speed -= STEP
        }

        return newLocation
    }



    companion object {
        const val MIN_X = 1
        const val MIN_Y = 1
        const val MAX_X = 10
        const val MAX_Y = 10
        const val LEFT = -1
        const val RIGHT = 1
        const val UP = -1
        const val DOWN = 1
        const val STEP = 1
        const val SEPARATOR = ", "
        val X_RANGE = MIN_X..MAX_X
        val Y_RANGE = MIN_Y..MAX_Y
        val INITIAL_LOCATION = Location(1, 1)

        fun getRandomLocation(): Location {
            val x = Random.nextInt(MIN_X..MAX_X)
            val y = Random.nextInt(MIN_Y..MAX_Y)
            return Location(x, y)
        }

        fun fromAddress(address: String): Location {
            val coordinates = address
                .split(SEPARATOR)
                .map { it.toIntOrNull() }
                .requireNoNulls()

            return Location(coordinates[0], coordinates[1])
        }
    }
}
