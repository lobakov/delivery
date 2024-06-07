package com.github.lobakov.delivery.core.application.usecase.shared

fun interface CommandHandler<C> {

    fun handle(command: C)
}
