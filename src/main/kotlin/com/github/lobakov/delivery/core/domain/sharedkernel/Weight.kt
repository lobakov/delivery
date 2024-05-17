package com.github.lobakov.delivery.core.domain.sharedkernel

data class Weight(
    val kg: Int
): Comparable<Weight> {

    init {
        require(MIN_WEIGHT <= kg) { "Weight should not be less than $MIN_WEIGHT kg" }
    }

    override fun compareTo(other: Weight): Int = compareValues(kg, other.kg)

    companion object {
        const val MIN_WEIGHT = 1
    }
}
