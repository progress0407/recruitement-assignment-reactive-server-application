package io.philo.query

import io.philo.dto.QueryResponse
import io.philo.shop.entity.Item
import io.philo.shop.infrastructure.ItemRepository
import io.philo.user.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class Query(private val userRepository: UserRepository, private val itemRepository: ItemRepository) {

    private val log = KotlinLogging.logger { }


    fun query() = runBlocking {

        val userDeferred = async { userRepository.findAll() }
        val itemDeferred = async { itemRepository.findAll() }

        val userIds: Flux<Long> = userDeferred.await().map { it.id!! }.onBackpressureDrop()
        val items: Flux<Item> = itemDeferred.await().onBackpressureDrop()

        val itemMonoList = items.collectList()

        val responseDto = userIds.flatMap { userId: Long ->
            val itemCntMono = getItemCntMono(userId, itemMonoList)
            convertResponseDto(userId, itemCntMono)
        }

        responseDto.subscribe { println(it) }

        return@runBlocking responseDto
    }

    private fun getItemCntMono(userId: Long, itemMonoList: Mono<MutableList<Item>>) =
        itemMonoList.map { it: MutableList<Item> ->
            it.filter { it.userId == userId }.size
        }

    private fun convertResponseDto(userId: Long, itemCntMono: Mono<Int>) =
        itemCntMono.flatMap { it: Int ->
            Mono.just(QueryResponse(userId, it))
        }

}