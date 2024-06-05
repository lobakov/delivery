package com.github.lobakov.delivery.api.adapters.http.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(CourierNotFoundException::class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    fun courierNotFoundException(e: CourierNotFoundException) {
        LOGGER.error(e.localizedMessage)
    }

    @ExceptionHandler(InvalidCourierStateException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun invalidCourierStateException(e: InvalidCourierStateException) {
        LOGGER.error(e.localizedMessage)
    }

    @ExceptionHandler(OrderNotFoundException::class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    fun courierNotFoundException(e: OrderNotFoundException) {
        LOGGER.error(e.localizedMessage)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)
    }
}
