package io.philo.presentation

import io.philo.dto.QueryResponse
import io.philo.query.Query
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/query")
class QueryController(private val query: Query) {


    /**
     * 사용자 한 명이 등록한 Item 목록 조회
     */
    @GetMapping("/user-statistics")
    fun query(): Flux<QueryResponse> {

        return query.query()
    }
}