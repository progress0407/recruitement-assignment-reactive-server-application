package io.philo.user.repository

import io.philo.user.entity.Users
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface UserRepository : ReactiveCrudRepository<Users, Long> {

    /**
     * todo findByName 되는지 확인
     */
    @Query("SELECT * FROM users WHERE name = :name")
    fun findByLastName(lastName: String?): Flux<Users>

    @Query("SELECT * FROM users WHERE email = :email")
    fun findByEmail(email: String): Mono<Users>
}