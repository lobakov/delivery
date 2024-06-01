package com.github.lobakov.delivery.core.application.shared

fun interface CommandHandler<C> {

    fun handle(command: C)
}
