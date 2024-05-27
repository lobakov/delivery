package com.github.lobakov.delivery.infrastructure.adapters.postgres.shared.mapper

interface Mapper<A, E> {

    fun toAggregate(entity: E): A

    fun toEntity(aggregate: A): E
}
