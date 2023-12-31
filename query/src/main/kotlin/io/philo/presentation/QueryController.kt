package io.philo.presentation

import io.philo.query.Query
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/query")
class QueryController(private val query: Query) {

    @GetMapping("/test")
    fun test() {
        query.test()
    }

    @PostMapping("/add")
    fun addSamples() {
        query.addSamples()
    }

    @GetMapping("/get")
    fun getSamples() {
        query.getSample()
    }
}