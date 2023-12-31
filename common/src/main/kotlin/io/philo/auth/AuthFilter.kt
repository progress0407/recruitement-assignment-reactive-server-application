package io.philo.auth

import io.philo.exception.constant.UnauthorizedException
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.*
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class AuthFilter(private val jwtManager: JwtManager) : WebFilter {

    private val log = KotlinLogging.logger { }

    private val requiredCredentials = listOf(
        EndPoint("/items", POST),
        EndPoint("/items", PUT),
        EndPoint("/items", DELETE)
    )

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {

        val request: ServerHttpRequest = exchange.request
        val method = request.method!!
        val path = request.path.toString()

        log.info { "it = $path" }

        if (isRequiredCredentials(path, method)) { // List로 뺄 것
            val accessToken = request.extractOrThrowToken()
            if (jwtManager.isValidToken(accessToken).not()) {
                throw UnauthorizedException("인증 토큰이 유효하지 않습니다.")
            }

            val userId = jwtManager.parse(accessToken)
            request.mutate().header("userId", userId).build()
        }

        return chain.filter(exchange).then(removeUserInfoIfExist(exchange))
    }

    private fun removeUserInfoIfExist(exchange: ServerWebExchange): Mono<Void> =
        Mono.defer {
            val responseHeaders = exchange.response.headers
            if (responseHeaders.containsKey("userId")) {
                responseHeaders.remove("userId")
            }
            Mono.empty()
        }


    private fun isRequiredCredentials(path: String, method: HttpMethod): Boolean {

        return requiredCredentials.any { path.contains(it.path)  && method == it.method  }
    }

    data class EndPoint(val path: String, val method: HttpMethod)

    private fun ServerHttpRequest.extractOrThrowToken(): String {

        val tokenString = this.headers[HttpHeaders.AUTHORIZATION] ?: throw UnauthorizedException("인증 토큰이 필요합니다.")
        return tokenString[0].removePrefix("Bearer ")
    }
}