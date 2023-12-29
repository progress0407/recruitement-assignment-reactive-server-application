package io.philo.user.repository

import io.philo.user.entity.Users
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux


interface UserRepository : ReactiveCrudRepository<Users, Long> {

    /**
     * todo findByName 되는지 확인
     */
    @Query("SELECT * FROM customer WHERE name = :name")
    fun findByLastName(lastName: String?): Flux<Users>
}