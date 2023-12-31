package io.philo.exception.constant

import org.springframework.http.HttpStatus

open class CustomException(val httpStatus: HttpStatus,
                           message: String = "",
                           cause: Throwable? = null) :
    RuntimeException(message, cause) {
}