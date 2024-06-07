package com.github.lobakov.delivery.core.application.usecase.shared

fun interface QueryHandler<C, R> {

    fun handle(query: C): R
}
