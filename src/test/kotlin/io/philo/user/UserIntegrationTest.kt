package io.philo.user

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.philo.dto.ResourceCreateResponse
import io.philo.support.IntegrationTest
import io.philo.user.entity.Users
import io.philo.user.presentation.dto.UserCreateRequest
import io.philo.user.presentation.dto.UserLoginRequest
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_JSON

class UserIntegrationTest : IntegrationTest() {

    private val log = KotlinLogging.logger { }

    /*
        @Test
        fun `회원가입 후 로그인한다`() {

            webTestClient.post().uri("/users")
                .contentType(APPLICATION_JSON)
                .bodyValue(UserCreateRequest.fixture)
                .exchange()
                .expectStatus().isCreated
                .expectBody(ResourceCreateResponse::class.java)

            webTestClient.post().uri("/users/login")
                .contentType(APPLICATION_JSON)
                .bodyValue(UserLoginRequest.fixture)
                .exchange()
                .expectStatus().isOk
            */
    /*.expectHeader().value(AUTHORIZATION) {
                it.shouldNotBeBlank()
            }*//*

    }
*/

    @Test
    fun `회원가입 후 로그인하면 토큰이 발급된다 1`() {

        val createdDto = webTestClient.post().uri("/users")
            .contentType(APPLICATION_JSON)
            .bodyValue(UserCreateRequest.fixture)
            .exchange()
            .expectStatus().isCreated
            .expectBody(ResourceCreateResponse::class.java)
            .returnResult()
            .responseBody

        createdDto shouldNotBe null
        (createdDto!!.id > 0L) shouldBe true

        val result2 = webTestClient.post().uri("/users/login")
            .contentType(APPLICATION_JSON)
            .bodyValue(UserLoginRequest.fixture)
            .exchange()
            .expectHeader()
            .exists(AUTHORIZATION)

        println("result2 = ${result2}")
    }

    @Test
    fun `회원가입 후 로그인하면 토큰이 발급된다 2`() {

        val createdDto = webTestClient.post().uri("/users")
            .contentType(APPLICATION_JSON)
            .bodyValue(UserCreateRequest.fixture)
            .exchange()
            .expectStatus().isCreated
            .expectBody(ResourceCreateResponse::class.java)
            .returnResult()
            .responseBody

        createdDto shouldNotBe null
        (createdDto!!.id > 0L) shouldBe true

        val result2 = webTestClient.post().uri("/users/header")
            .contentType(APPLICATION_JSON)
            .bodyValue(UserLoginRequest.fixture)
            .exchange()
            .expectHeader()
            .exists(AUTHORIZATION)

        println("result2 = ${result2}")
    }


    @Test
    fun `test`() {

        log.info { "this is time check start" }

        val result1 = webTestClient.get().uri("/users/1")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        val result2 = webTestClient.get().uri("/users/2")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        result1 shouldBe "this is 1"
        result2 shouldBe "this is 2"

        log.info { "this is time check start" }
    }

    val Users.Companion.fixtureEmail get() = "philo@philo.io"
    val Users.Companion.fixtureName get() = "philo"
    val Users.Companion.fixtureRawPassword get() = "1234"

    val UserCreateRequest.Companion.fixture
        get() = UserCreateRequest(
            email = Users.fixtureEmail,
            name = Users.fixtureName,
            password = Users.fixtureRawPassword
        )

    val UserLoginRequest.Companion.fixture
        get() = UserLoginRequest(
            id = Users.fixtureEmail,
            password = Users.fixtureRawPassword
        )
}