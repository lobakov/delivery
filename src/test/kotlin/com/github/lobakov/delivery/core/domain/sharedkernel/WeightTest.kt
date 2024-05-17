package com.github.lobakov.delivery.core.domain.sharedkernel

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WeightTest {

    @ParameterizedTest
    @ValueSource(ints = [1, 13, 25, 500, 15, Integer.MAX_VALUE])
    fun `should allow create weight with valid amount of kilograms`(kg: Int) {
        val sut = Weight(kg)

        Assertions.assertEquals(kg, sut.kg)
    }

    @ParameterizedTest
    @ValueSource(ints = [Integer.MIN_VALUE, -10, -1, 0])
    fun `should throw IllegalArgumentException when x or y is out of range`(kg: Int) {
        assertThrows<IllegalArgumentException> { Weight(kg) }
    }

    @ParameterizedTest
    @MethodSource("generateComparableWeights")
    fun `should properly compare weights`(args: Pair<Weight, Int>) {
        val sut = Weight(5)
        val anotherWeight = args.first
        val expectedResult = args.second

        val actualResult = sut.compareTo(anotherWeight)

        Assertions.assertEquals(expectedResult, actualResult)
    }


    companion object {
        @JvmStatic
        fun generateComparableWeights() = listOf(
            Weight(4) to 1,
            Weight(5) to 0,
            Weight(6) to -1
        )
    }
}
