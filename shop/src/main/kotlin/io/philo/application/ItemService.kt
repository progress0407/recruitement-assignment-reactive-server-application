package io.philo.application

import io.philo.entity.Item
import io.philo.infrastructure.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux

@Service
@Transactional(readOnly = true)
class ItemService(private val itemRepository: ItemRepository) {

    @Transactional
    fun registerItem(
        name: String,
        price: Int,
        availableQuantity: Int
    ): Long {

        val item = Item(name, price, availableQuantity)
        itemRepository.save(item)

        return item.id!!
    }

    @Transactional(readOnly = true)
    fun findItems(): Flux<Item> {

        return itemRepository.findAll()
    }
}