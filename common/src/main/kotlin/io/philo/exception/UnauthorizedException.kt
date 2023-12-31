package io.philo.exception

import org.springframework.http.HttpStatus

open class UnauthorizedException(message: String?, cause: Throwable?) : RuntimeException(message, cause) {

    private val httpStatus = HttpStatus.UNAUTHORIZED // 401

    constructor(message: String): this(message, null)
}