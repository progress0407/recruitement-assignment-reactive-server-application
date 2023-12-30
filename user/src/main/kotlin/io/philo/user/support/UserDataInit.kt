package io.philo.user.support

import io.philo.user.entity.Users
import io.philo.user.repository.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class UserDataInit(private val userRepository: UserRepository) {

    val log = KotlinLogging.logger {  }

//    @PostConstruct
    fun init() {
        userRepository.saveAll(
            mutableListOf(
                Users("a@philo.com", "name-a", "1234"),
                Users("b@philo.com", "name-b", "1234"),
                Users("c@philo.com", "name-c", "1234")
            )
        )
//            .blockLast(Duration.ofSeconds(1))
        val flux = userRepository.findAll().log()
    }
}