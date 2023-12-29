package io.philo

import io.philo.user.entity.Users
import io.philo.user.repository.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import java.time.Duration

@SpringBootApplication
class ReactiveServerApplication {

    @Autowired
    private lateinit var applicationContext:ApplicationContext

    private val log = KotlinLogging.logger {  }

/*
    @PostConstruct
    fun check() {
        println(applicationContext)
    }
*/

    @Bean
    fun demo(repository: UserRepository): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            repository.saveAll(
                listOf(
                    Users("A"),
                    Users("B"),
                    Users("c")
                )
            )
                .blockLast(Duration.ofSeconds(10))
            val list = repository.findAll()
            log.info { "list = $list" }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ReactiveServerApplication>(*args)
}