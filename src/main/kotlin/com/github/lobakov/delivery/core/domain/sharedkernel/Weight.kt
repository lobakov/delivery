package com.github.lobakov.delivery.core.domain.sharedkernel

data class Weight(
    val kg: Int
): Comparable<Weight> {

    init {
        require(MIN_WEIGHT <= kg) { "Weight cannot be less than 1 kg" }
    }

    override fun compareTo(other: Weight): Int = compareValues(kg, other.kg)

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        kg == (other as Weight).kg -> true
        else -> false
    }

    override fun hashCode(): Int = HASH_MULTIPLIER * kg

    override fun toString(): String = "weight = $kg kg"

    companion object {
        private const val HASH_MULTIPLIER = 31
        private const val MIN_WEIGHT = 1
    }
}
