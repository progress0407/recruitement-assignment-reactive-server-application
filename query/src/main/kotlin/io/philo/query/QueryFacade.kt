package io.philo.query

import io.philo.shop.entity.Item
import io.philo.user.entity.Users
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class QueryFacade(private val query: Query) {

    fun getSample() = runBlocking {

        val sample: Pair<Flux<Users>, Flux<Item>> = query.getSample()
        val (users, items) = sample

        users.blockFirst()
    }
}
