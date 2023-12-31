package io.philo.user.presentation

import io.philo.dto.ResourceCreateResponse
import io.philo.user.application.UserService
import io.philo.user.entity.Users
import io.philo.user.presentation.dto.UserCreateRequest
import io.philo.user.presentation.dto.UserLoginRequest
import io.philo.user.repository.UserRepository
import mu.KotlinLogging
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class UserController(
    private val service: UserService,
    private val repository: UserRepository,
) {

    private val log = KotlinLogging.logger { }

    @PostMapping
    @ResponseStatus(CREATED)
    fun save(@RequestBody request: UserCreateRequest): Mono<ResourceCreateResponse> {

        val idMono = service.save(request.email, request.name, request.password)
        return idMono.map { ResourceCreateResponse(it) }
    }

    @GetMapping
    fun findAll(): Flux<Users> {

        return repository.findAll().log()
    }

    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): Mono<ResponseEntity<String>> {

        return service.login(request.id, request.password)
            .doOnNext { log.info { "accessToken: $it" } }
            .map { token: String ->
                ResponseEntity.ok()
                    .header(AUTHORIZATION, token)
                    .body("User logged in successfully. See response header")
            }
    }

    @PostMapping("/header")
    fun header(): Mono<ResponseEntity<String>> {

        val entity = ResponseEntity.ok()
            .header(AUTHORIZATION, "hello")
            .body("User logged in successfully. See response header")

        return Mono.just(entity)
    }

    @GetMapping("/1")
    fun get1(): Mono<String> {

        log.info { "hello this is 1" }

        Thread.sleep(2L)

        return Mono.just("this is 1")
    }

    @GetMapping("/2")
    fun get2(): Mono<String> {

        log.info { "hello this is 2" }

        Thread.sleep(2L)

        return Mono.just("this is 2")
    }
}
