package io.philo.infrastructure

import io.philo.entity.Item
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ItemRepository: ReactiveCrudRepository<Item, Long> {
}