package io.philo.shop.application

import io.philo.exception.constant.EntityNotFoundException
import io.philo.shop.entity.Item
import io.philo.shop.infrastructure.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@Transactional(readOnly = true)
class ItemService(private val repository: ItemRepository) {

    @Transactional
    fun registerItem(
        name: String,
        price: Int,
        availableQuantity: Int,
        userId: Long,
    ): Mono<Long> {

        val item = Item(name, price, availableQuantity, userId)
        val savedId = repository.save(item).map { it.id!! }

        return savedId
    }

    @Transactional
    fun update(id: Long, name: String, price: Int, stockQuantity: Int): Mono<Void> {

        return repository.findById(id)
            .switchIfEmpty(deferredError(id))
            .flatMap { item ->
            item.updateInfo(name, price, stockQuantity)
            repository.save(item)
        }.then()
    }

    @Transactional
    fun delete(id: Long): Mono<Void> {

        return repository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun findItems(): Flux<Item> {

        return repository.findAll()
    }

    private fun deferredError(info: Long): Mono<Item> {

        val exception = EntityNotFoundException(info.toString())
        return Mono.defer { Mono.error(exception) }
    }
}