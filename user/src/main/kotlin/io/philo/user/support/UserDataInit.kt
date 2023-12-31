package io.philo.user.support

import io.philo.user.entity.User
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
                User("a@philo.com", "name-a", "1234"),
                User("b@philo.com", "name-b", "1234"),
                User("c@philo.com", "name-c", "1234")
            )
        )
//            .blockLast(Duration.ofSeconds(1))
        val flux = userRepository.findAll().log()
    }
}