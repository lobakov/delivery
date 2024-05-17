package com.github.lobakov.delivery.core.domain.sharedkernel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class LocationTest {

    @ParameterizedTest
    @MethodSource("generateValidCoordinates")
    fun `should allow create location with valid coordinates`(coordinate: Coordinate) {
        val expectedX = coordinate.x
        val expectedY = coordinate.y

        val sut = Location(expectedX, expectedY)

        assertAll(
            "Ensure coordinates were validated and set up properly",
            { assertEquals(expectedX, sut.x) },
            { assertEquals(expectedY, sut.y) }
        )
    }

    @ParameterizedTest
    @MethodSource("generateInvalidCoordinates")
    fun `should throw IllegalArgumentException when x or y is out of range`(coordinate: Coordinate) {
        val expectedExceptionMessage = "Location coordinates are out of valid range. Should be in range [1..10]"
        val expectedX = coordinate.x
        val expectedY = coordinate.y

        val actualException = assertThrows<IllegalArgumentException> { Location(expectedX, expectedY) }
        assertEquals(expectedExceptionMessage, actualException.message)
    }

    @ParameterizedTest
    @MethodSource("generateTargetLocations")
    fun `should properly count distance to target location`(targetLocationAndExpectedDistance: TargetLocationAndExpectedDistance) {
        val sut = Location(1, 1)
        val target = targetLocationAndExpectedDistance.target
        val expectedDistance = targetLocationAndExpectedDistance.expectedDistance

        val actualDistance = sut.distanceTo(target)

        assertEquals(expectedDistance, actualDistance)
    }

    companion object {
        @JvmStatic
        fun generateValidCoordinates() = listOf(
            Coordinate(1, 1),
            Coordinate(3, 2),
            Coordinate(10, 1),
            Coordinate(1, 10),
            Coordinate(10, 10)
        )

        @JvmStatic
        fun generateInvalidCoordinates() = listOf(
            Coordinate(-1, 1),
            Coordinate(3, -2),
            Coordinate(0, 11),
            Coordinate(1, 11),
            Coordinate(11, 1),
            Coordinate(11, 0)
        )

        @JvmStatic
        fun generateTargetLocations() = listOf(
            TargetLocationAndExpectedDistance(Location(2, 2), 2),
            TargetLocationAndExpectedDistance(Location(3, 3), 4),
            TargetLocationAndExpectedDistance(Location(8, 7), 13),
            TargetLocationAndExpectedDistance(Location(10, 10), 18),
        )
    }
}

data class TargetLocationAndExpectedDistance(val target: Location, val expectedDistance: Int)

data class Coordinate(val x: Int, val y: Int)
