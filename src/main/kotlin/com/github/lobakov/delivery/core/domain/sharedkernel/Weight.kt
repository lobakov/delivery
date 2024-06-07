package com.github.lobakov.delivery.core.domain.sharedkernel

import kotlin.random.Random
import kotlin.random.nextInt

data class Weight(
    val kg: Int
): Comparable<Weight> {

    init {
        require(kg in ALLOWED_WEIGHT_RANGE) {
            "Weight is out of valid range. Weight should be in range [$MIN_WEIGHT..$MAX_WEIGHT]"
        }
    }

    override fun compareTo(other: Weight): Int = compareValues(kg, other.kg)

    companion object {
        const val MIN_WEIGHT = 1
        const val MAX_WEIGHT = 8
        val ALLOWED_WEIGHT_RANGE = MIN_WEIGHT..MAX_WEIGHT

        fun getRandomWeight() = Weight(
            Random.nextInt(ALLOWED_WEIGHT_RANGE)
        )
    }
}
