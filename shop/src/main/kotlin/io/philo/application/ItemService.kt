package io.philo.application

import io.philo.entity.Item
import io.philo.infrastructure.ItemRepository
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
        availableQuantity: Int
    ): Mono<Long> {

        val item = Item(name, price, availableQuantity)
        val savedId = itemRepository.save(item).map { it.id!! }

        return savedId
    }

    @Transactional(readOnly = true)
    fun findItems(): Flux<Item> {

        return itemRepository.findAll()
    }
}