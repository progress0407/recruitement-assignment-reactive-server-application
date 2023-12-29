package io.philo.user.presentation

import io.philo.user.application.UserService
import io.philo.user.entity.Users
import io.philo.user.presentation.dto.UserCreateRequest
import io.philo.user.presentation.dto.UserCreateResponse
import io.philo.user.repository.UserRepository
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class UserController(
    private val service: UserService,
    private val repository: UserRepository
) {

    @GetMapping
    fun findAll(): Flux<Users> {
        val flux: Flux<Users> = repository.findAll().log()
        return flux
    }

    @GetMapping("/test")
    fun test(): Mono<String> {
        return Mono.just("hello")
    }

    @PostMapping
    @ResponseStatus(OK)
    fun save(@RequestBody request: UserCreateRequest): Mono<UserCreateResponse> {
        val savedIdMono = service.save(request.name)
        return savedIdMono.map { UserCreateResponse(it) }
    }
}
