package io.philo.user.presentation

import io.philo.dto.ResourceCreateResponse
import io.philo.user.application.UserService
import io.philo.user.presentation.dto.UserCreateRequest
import io.philo.user.presentation.dto.UserListResponse
import io.philo.user.presentation.dto.UserLoginRequest
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
) {

    private val log = KotlinLogging.logger { }

    @PostMapping
    @ResponseStatus(CREATED)
    fun save(@RequestBody request: UserCreateRequest): Mono<ResourceCreateResponse> {

        val idMono = service.save(request.email, request.name, request.password)
        return idMono.map { ResourceCreateResponse(it) }
    }

    @GetMapping
    fun findAll(): Flux<UserListResponse> {

        return service.findAll()
            .map { UserListResponse(it) }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): Mono<ResponseEntity<String>> {

        return service.login(request.id, request.password)
            .map { token: String ->
                ResponseEntity.ok()
                    .header(AUTHORIZATION, token)
                    .body("User logged in successfully. See response header")
            }
    }
}
