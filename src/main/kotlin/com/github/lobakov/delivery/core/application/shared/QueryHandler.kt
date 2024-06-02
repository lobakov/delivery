package com.github.lobakov.delivery.core.application.shared

fun interface QueryHandler<C, R> {

    fun handle(command: C): R
}
