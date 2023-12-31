package io.philo.shop.application

import io.philo.shop.entity.Item
import io.philo.shop.infrastructure.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@Transactional(readOnly = true)
class ItemService(private val itemRepository: ItemRepository) {

    @Transactional
    fun registerItem(
        name: String,
        price: Int,
        availableQuantity: Int,
        userId: Long
    ): Mono<Long> {

        val item = Item(name, price, availableQuantity, userId)
        val savedId = itemRepository.save(item).map { it.id!! }

        return savedId
    }

    @Transactional(readOnly = true)
    fun findItems(): Flux<Item> {

        return itemRepository.findAll()
    }
}