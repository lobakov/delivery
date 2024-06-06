package com.github.lobakov.delivery.core.domain.sharedkernel

import com.github.lobakov.delivery.core.domain.sharedkernel.Weight.Companion.MAX_WEIGHT
import com.github.lobakov.delivery.core.domain.sharedkernel.Weight.Companion.MIN_WEIGHT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class WeightTest {

    @ParameterizedTest
    @ValueSource(ints = [1, 3, 5, 7, 8])
    fun `should allow create weight with valid amount of kilograms`(kg: Int) {
        val sut = Weight(kg)

        assertEquals(kg, sut.kg)
    }

    @ParameterizedTest
    @ValueSource(ints = [Integer.MIN_VALUE, -10, -1, 0, 9])
    fun `should throw IllegalArgumentException when x or y is out of range`(kg: Int) {
        val expectedExceptionMessage = "Weight is out of valid range. Weight should be in range [$MIN_WEIGHT..$MAX_WEIGHT]"

        val exception = assertThrows<IllegalArgumentException> { Weight(kg) }

        assertEquals(expectedExceptionMessage, exception.message)
    }

    @ParameterizedTest
    @MethodSource("generateComparableWeights")
    fun `should properly compare weights`(weightToCompareAndExpectedRelation: WeightToCompareAndExpectedRelation) {
        val sut = Weight(5)
        val anotherWeight = weightToCompareAndExpectedRelation.weight
        val expectedRelation = weightToCompareAndExpectedRelation.expectedRelation

        val actualRelation = sut.compareTo(anotherWeight)

        assertEquals(expectedRelation, actualRelation)
    }


    companion object {
        private const val LESS = -1
        private const val GREATER = 1
        private const val EQUAL = 0

        @JvmStatic
        fun generateComparableWeights() = listOf(
            WeightToCompareAndExpectedRelation(Weight(4), GREATER),
            WeightToCompareAndExpectedRelation(Weight(5), EQUAL),
            WeightToCompareAndExpectedRelation(Weight(6), LESS)
        )
    }
}

data class WeightToCompareAndExpectedRelation(val weight: Weight, val expectedRelation: Int)
