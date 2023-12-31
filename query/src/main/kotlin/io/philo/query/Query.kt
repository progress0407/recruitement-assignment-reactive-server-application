package io.philo.query

import io.philo.dto.QueryResponse
import io.philo.shop.entity.Item
import io.philo.shop.infrastructure.ItemRepository
import io.philo.user.entity.Users
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

    fun query(userIds: List<Long>) {

        userRepository.findByIdIn(userIds)
        itemRepository.findByUserIdIn(userIds)
    }

    /*

    // 일부만 저장된
        fun addSamples() {

            val userA = Users("a@a.com", "a", "1234")
            val userB = Users("b@a.com", "b", "1234")
            val userC = Users("c@a.com", "c", "1234")

            val saveUsers = Flux.just(userA, userB, userC)
                .flatMap { userRepository.save(it) }
                .collectList()

            saveUsers.map { users ->
                val items: List<Item> = users.map { user ->
                    when (user.email) {
                        "a@a.com" -> Item("item-a", 100, 100, user.id!!)
                        "b@a.com" -> Item("item-b", 200, 200, user.id!!)
                        else -> null
                    }
                }.filterNotNull()
                itemRepository.saveAll(items)
            }
        }
    */

    fun addSamples() {

        val userA = Users("a@a.com", "a", "1234")
        val userB = Users("b@a.com", "b", "1234")
        val userC = Users("c@a.com", "c", "1234")

        val saveUsers = Flux.just(userA, userB, userC)
            .flatMap { userRepository.save(it) }
            .collectList()

        saveUsers.map { users ->
            val items: List<Item> = users.map { user ->
                when (user.email) {
                    "a@a.com" -> Item("item-a", 100, 100, user.id!!)
                    "b@a.com" -> Item("item-b", 200, 200, user.id!!)
                    else -> null
                }
            }.filterNotNull()
            itemRepository.saveAll(items)
        }
    }

    fun getSample(): Pair<Flux<Users>, Flux<Item>> = runBlocking {

        val userDeferred = async { userRepository.findAll() }
        val itemDeferred = async { itemRepository.findAll() }

        val users: Flux<Users> = userDeferred.await()
        val items: Flux<Item> = itemDeferred.await()

        users.doOnNext { println(it) }
        items.doOnNext { println(it) }

        items.blockFirst()

        return@runBlocking Pair(users, items)
    }

    fun getSample2() = runBlocking {

        val userDeferred = async { userRepository.findAll() }
        val itemDeferred = async { itemRepository.findAll() }

        val userIds: Flux<Long> = userDeferred.await().map { it.id!! }
        val items: Flux<Item> = itemDeferred.await()

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