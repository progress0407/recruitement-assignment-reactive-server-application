package io.philo.user.application

import io.philo.user.entity.Users
import io.philo.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class UserService(private val repository: UserRepository) {

    @Transactional
    fun save(name: String): Mono<Long> {

        val user = Users(name)
        return repository.save(user).mapNotNull { it.id }
    }
}