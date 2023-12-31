package io.philo.exception.handler

import com.google.gson.Gson
import io.philo.exception.constant.CustomException
import mu.KotlinLogging
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
//@Order(HIGHEST_PRECEDENCE)
class GlobalExceptionHandler : ErrorWebExceptionHandler {

    private val log = KotlinLogging.logger {  }

    private val gson = Gson()

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {

        log.error { ex }

        val response = exchange.response
        setJsonContentType(response)
        setHttpStatusCode(ex, response)
        val responseBody = createResponseBody(ex, response)

        return response.writeWith(responseBody)
    }

    private fun setJsonContentType(response: ServerHttpResponse) {
        response.headers.contentType = MediaType.APPLICATION_JSON
    }

    private fun setHttpStatusCode(ex: Throwable, response: ServerHttpResponse) {
        if (ex is CustomException) {
            response.statusCode = ex.httpStatus
        } else {
            response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        }
    }

    private fun createResponseBody(
        ex: Throwable,
        response: ServerHttpResponse,
    ): Mono<DataBuffer> {
        val responseBody = ErrorResponse("${ex.message}")
        val responseJsonString = gson.toJson(responseBody)
        val buffer = convertDataBuffer(response, responseJsonString)
        val mono = Mono.just(buffer)
        return mono
    }

    private fun convertDataBuffer(
        response: ServerHttpResponse,
        responseJsonString: String,
    ) = response.bufferFactory().wrap(responseJsonString.toByteArray(Charsets.UTF_8))
}

data class ErrorResponse(private val errorMessage: String)