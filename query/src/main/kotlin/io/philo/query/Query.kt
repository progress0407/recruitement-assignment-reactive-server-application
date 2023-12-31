package io.philo.query

import io.philo.shop.infrastructure.ItemRepository
import io.philo.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class Query(private val userRepository: UserRepository, private val itemRepository: ItemRepository) {

    fun query() {

    }
}