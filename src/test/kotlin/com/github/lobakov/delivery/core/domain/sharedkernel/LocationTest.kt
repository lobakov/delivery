package com.github.lobakov.delivery.core.domain.sharedkernel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LocationTest {

    @ParameterizedTest
    @MethodSource("generateValidArgs")
    fun `should allow create location with valid coordinates`(args: Pair<Int, Int>) {
        val expectedX = args.first
        val expectedY = args.second

        val sut = Location(expectedX, expectedY)

        assertEquals(expectedX, sut.x)
        assertEquals(expectedY, sut.y)
    }

    @ParameterizedTest
    @MethodSource("generateInvalidArgs")
    fun `should throw IllegalArgumentException when x or y is out of range`(args: Pair<Int, Int>) {
        val expectedX = args.first
        val expectedY = args.second

        assertThrows<IllegalArgumentException> {  Location(expectedX, expectedY) }
    }

    @ParameterizedTest
    @MethodSource("generateTargetLocations")
    fun `should properly count distance to target location`(args: Pair<Location, Int>) {
        val sut = Location(1, 1)
        val target = args.first
        val expectedResult = args.second

        val actualResult = sut.distanceTo(target)

        assertEquals(expectedResult, actualResult)
    }

    companion object {
        @JvmStatic
        fun generateValidArgs() = listOf(
            1 to 1,
            3 to 2,
            10 to 1,
            1 to 10,
            10 to 10
        )

        @JvmStatic
        fun generateInvalidArgs() = listOf(
            -1 to 1,
            3 to -2,
            0 to 11,
            11 to 0,
            1 to 11,
            11 to 1
        )

        @JvmStatic
        fun generateTargetLocations() = listOf(
            Location(2, 2) to 2,
            Location(3, 3) to 4,
            Location(8, 7) to 13,
            Location(10,10) to 18,
        )
    }
}
