package com.github.lobakov.delivery.api.adapters.mediator

fun interface Mediator<C> {

    fun mediate(command: C)
}
