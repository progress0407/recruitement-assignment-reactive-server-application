package io.philo.exception.constant

import org.springframework.http.HttpStatus.UNAUTHORIZED

/**
 * 401 예외
 */
open class UnauthorizedException(message: String, cause: Throwable? = null) :
    CustomException(UNAUTHORIZED, message, cause)