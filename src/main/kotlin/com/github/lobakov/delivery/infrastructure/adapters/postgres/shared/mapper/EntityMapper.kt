package com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.mapper

interface EntityMapper<A, E> {

    fun toAggregate(entity: E): A

    fun toEntity(aggregate: A): E
}
