package io.philo.auth

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SecurityConfig {

    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512)
    private val expirationDurationTime: Long = 60 * 60 * 1000

    @Bean
    fun jwtManager() = JwtManager(secretKey, expirationDurationTime)
}