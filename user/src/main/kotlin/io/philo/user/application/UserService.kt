package io.philo.user.application

import io.philo.auth.JwtManager
import io.philo.exception.constant.EntityNotFoundException
import io.philo.exception.constant.UnauthorizedException
import io.philo.user.entity.User
import io.philo.user.repository.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(
    private val jwtManager: JwtManager,
    private val repository: UserRepository
) {

    private val log = KotlinLogging.logger {  }

    @Transactional
    fun save(email: String, name: String, password: String): Mono<Long> {

        val user = User(email, name, password)
        return repository.save(user).mapNotNull { it.id }
    }

    @Transactional(readOnly = true)
    fun findAll(): Flux<User> {

        return repository.findAll()
    }

    @Transactional(readOnly = true)
    fun login(email: String, inputPassword: String): Mono<String> {

        return repository.findByEmail(email)
            .switchIfEmpty(deferredError(email))
            .map { user ->
                validateCredential(inputPassword, user)
                val subject = user.id.toString()
                val accessToken = jwtManager.createAccessToken(subject)
                accessToken
            }
    }

    private fun validateCredential(inputPassword: String, user: User) {

        if (isCorrectCredential(inputPassword, user).not()) {
            throw UnauthorizedException("유효한 로그인 정보가 아닙니다.")
        }
    }

    private fun isCorrectCredential(inputPassword: String, user: User): Boolean {

        return user.isSamePassword(inputPassword)
    }

    private fun deferredError(info: String): Mono<User> {

        val exception = EntityNotFoundException(info)
        return Mono.defer { Mono.error(exception) }
    }
}