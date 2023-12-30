package io.philo.user.application

import io.philo.shop.error.UnauthorizedException
import io.philo.support.JwtManager
import io.philo.user.entity.Users
import io.philo.user.exception.EntityNotFoundException
import io.philo.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class UserService(
    private val jwtManager: JwtManager,
    private val repository: UserRepository
) {

    @Transactional
    fun save(email: String, name: String, password: String): Mono<Long> {

        val user = Users(email, name, password)
        return repository.save(user).mapNotNull { it.id }
    }

    @Transactional(readOnly = true)
    fun login(email: String, password: String): String {

        val user = repository.findByEmail(email).block() ?: throw EntityNotFoundException(email)

        validateCredential(password, user)

        val subject = user.id.toString()
        val newAccessToken = jwtManager.createAccessToken(subject)

        return newAccessToken
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