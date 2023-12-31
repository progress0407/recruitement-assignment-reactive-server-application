package io.philo.shop.infrastructure

import io.philo.shop.entity.Item
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ItemRepository: ReactiveCrudRepository<Item, Long> {

    fun findByUserIdIn(ids: Collection<Long>): Flux<Item>
}