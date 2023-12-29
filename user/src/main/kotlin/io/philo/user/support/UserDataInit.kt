package io.philo.user.support

import io.philo.user.entity.Users
import io.philo.user.repository.UserRepository
import mu.KotlinLogging
import java.time.Duration

//@Component
class UserDataInit(private val userRepository: UserRepository) {

    val log = KotlinLogging.logger {  }

//    @PostConstruct
    fun init() {
        userRepository.saveAll(
            mutableListOf(
                Users("A"),
                Users("B"),
                Users("c")
            )
        ).blockLast(Duration.ofSeconds(1))
        val flux = userRepository.findAll()
        log.info { "flux = $flux" }
    }
}