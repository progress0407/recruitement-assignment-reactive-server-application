package io.philo.exception.constant

import org.springframework.http.HttpStatus.NOT_FOUND

open class NotFoundException(message: String = "", cause: Throwable? = null) :
    CustomException(NOT_FOUND, message, cause)