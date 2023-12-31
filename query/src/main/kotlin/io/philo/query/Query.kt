package io.philo.query

import io.philo.shop.entity.Item
import io.philo.shop.infrastructure.ItemRepository
import io.philo.user.entity.Users
import io.philo.user.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import kotlin.system.measureTimeMillis

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


    fun getSample() = runBlocking {

        val userDeferred = async { userRepository.findAll() }
        val itemDeferred = async { itemRepository.findAll() }

        val users = userDeferred.await()
        val items = itemDeferred.await()

        println("users = ${users}")
        println("items = ${items}")
    }

    fun test() = runBlocking {

        val timeMillis = measureTimeMillis {
            println("run a async")
            val aDiffered = async { a() }
            println("run b async")
            val bDiffered = async { b() }
            println(aDiffered.await())
            println(bDiffered.await())
        }

        log.info { "timeMillis = ${timeMillis}" }
    }

    private suspend fun a(): String {
        delay(2000)
        return "hi-a"
    }

    private suspend fun b(): String {
        delay(2000)
        return "hi-2"
    }
}