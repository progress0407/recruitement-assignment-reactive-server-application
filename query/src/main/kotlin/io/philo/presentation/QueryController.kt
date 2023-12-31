package io.philo.presentation

import io.philo.dto.QueryResponse
import io.philo.query.Query
import io.philo.query.QueryFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/query")
class QueryController(private val query: Query, private val queryFacade: QueryFacade) {


    @PostMapping("/add")
    fun addSamples() {
        query.addSamples()
    }

    @GetMapping("/get")
    fun getSamples() {
        queryFacade.getSample()
    }

    @GetMapping("/get2")
    fun getSamples2(): Flux<QueryResponse> {

        val result: Flux<QueryResponse> = query.getSample2()

        return result
    }

}