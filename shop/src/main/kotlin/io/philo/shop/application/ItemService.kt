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

    @Transactional
    fun update(id: Long, name: String, price: Int, stockQuantity: Int): Mono<Void> {

        val foundItem = itemRepository.findById(id)

        return foundItem.flatMap { item ->
            item.updateInfo(name, price, stockQuantity)
            itemRepository.save(item)
        }.then()
    }

    @Transactional(readOnly = true)
    fun findItems(): Flux<Item> {

        return itemRepository.findAll()
    }
}