package io.philo.user.repository

import io.philo.user.entity.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface UserRepository : ReactiveCrudRepository<User, Long> {

    fun findByEmail(email: String): Mono<User>

    fun findByIdIn(ids: Collection<Long>): Flux<User>
}