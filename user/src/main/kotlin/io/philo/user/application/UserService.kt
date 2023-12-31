package io.philo.user.application

import io.philo.auth.JwtManager
import io.philo.exception.UnauthorizedException
import io.philo.user.entity.Users
import io.philo.user.repository.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class UserService(
    private val jwtManager: JwtManager,
    private val repository: UserRepository
) {

    private val log = KotlinLogging.logger {  }

    @Transactional
    fun save(email: String, name: String, password: String): Mono<Long> {

        val user = Users(email, name, password)
        return repository.save(user).mapNotNull { it.id }
    }

    /*
    todo EntityNotFoundException 예외처리기 달기! 500 -> 401
     */
    fun login(email: String, inputPassword: String): Mono<String> {

        return repository.findByEmail(email)
            .doOnNext { log.info { "user: $it" } }
//            .switchIfEmpty(Mono.error(EntityNotFoundException(email)))
            .map { user ->
                validateCredential(inputPassword, user)
                val subject = user.id.toString()
                val accessToken = jwtManager.createAccessToken(subject)
                accessToken
            }
    }

    private fun validateCredential(inputPassword: String, user: Users) {
        if (isCorrectCredential(inputPassword, user).not()) {
            throw UnauthorizedException("유효한 로그인 정보가 아닙니다.")
        }
    }

    private fun isCorrectCredential(inputPassword: String, user: Users): Boolean {
        return user.isSamePassword(inputPassword)
    }
}