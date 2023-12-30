package io.philo.shop.infrastructure

import io.philo.shop.entity.Item
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ItemRepository: ReactiveCrudRepository<Item, Long> {
}