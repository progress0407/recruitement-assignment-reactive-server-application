package io.philo.user.repository

import io.philo.user.entity.Users
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface UserRepository : ReactiveCrudRepository<Users, Long> {

    fun findByEmail(email: String): Mono<Users>

    fun findByIdIn(ids: Collection<Long>): Flux<Users>
}