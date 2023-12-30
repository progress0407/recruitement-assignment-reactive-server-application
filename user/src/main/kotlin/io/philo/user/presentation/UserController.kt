package io.philo.user.presentation

import io.philo.user.application.UserService
import io.philo.user.entity.Users
import io.philo.user.presentation.dto.UserCreateRequest
import io.philo.user.presentation.dto.UserCreateResponse
import io.philo.user.presentation.dto.UserLoginRequest
import io.philo.user.repository.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class UserController(
    private val service: UserService,
    private val repository: UserRepository
) {

    @PostMapping
    @ResponseStatus(OK)
    fun save(@RequestBody request: UserCreateRequest): Mono<UserCreateResponse> {

        val idMono = service.save(request.email, request.name, request.password)
        return idMono.map { UserCreateResponse(it) }
    }

    @GetMapping
    fun findAll(): Flux<Users> {

        return repository.findAll().log()
    }

    @GetMapping("/test")
    fun test(): Mono<String> {

        return Mono.just("hello")
    }

    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): ResponseEntity<*> {

        val accessToken = service.login(request.id, request.password)

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .body("User logged in successfully. See response header")
    }
}
