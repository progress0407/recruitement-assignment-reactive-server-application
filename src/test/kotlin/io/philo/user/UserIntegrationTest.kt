package io.philo.user

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.philo.dto.ResourceCreateResponse
import io.philo.support.IntegrationTest
import io.philo.user.entity.User
import io.philo.user.presentation.dto.UserCreateRequest
import io.philo.user.presentation.dto.UserLoginRequest
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_JSON

class UserIntegrationTest : IntegrationTest() {

    private val log = KotlinLogging.logger { }

    @Test
    fun `회원가입 후 로그인하면 토큰이 발급된다`() {

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

        webTestClient.post().uri("/users/login")
            .contentType(APPLICATION_JSON)
            .bodyValue(UserLoginRequest.fixture)
            .exchange()
            .expectHeader()
            .exists(AUTHORIZATION)
    }


    val User.Companion.fixtureEmail get() = "philo@philo.io"
    val User.Companion.fixtureName get() = "philo"
    val User.Companion.fixtureRawPassword get() = "1234"

    val UserCreateRequest.Companion.fixture
        get() = UserCreateRequest(
            email = User.fixtureEmail,
            name = User.fixtureName,
            password = User.fixtureRawPassword
        )

    val UserLoginRequest.Companion.fixture
        get() = UserLoginRequest(
            id = User.fixtureEmail,
            password = User.fixtureRawPassword
        )
}