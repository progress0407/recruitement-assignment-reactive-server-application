package io.philo.integration.item

import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.philo.auth.JwtManager
import io.philo.dto.ResourceCreateResponse
import io.philo.integration.support.IntegrationTest
import io.philo.shop.dto.ItemCreateRequest
import io.philo.shop.dto.ItemListResponse
import io.philo.shop.entity.Item
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_JSON


class ItemIntegrationTest: IntegrationTest() {

    @MockkBean
    lateinit var jwtManager: JwtManager

    @Test
    fun `재고 등록 및 조회`() {

        every { jwtManager.isValidToken(any()) } returns true
        every { jwtManager.parse(any()) } returns "1"


        val createdDto = webTestClient.post().uri("/items")
            .headers { headers ->
                headers.add(AUTHORIZATION, "access token here")
                headers.add("userId", "1")
            }
            .contentType(APPLICATION_JSON)
            .bodyValue(ItemCreateRequest.fixture)
            .exchange()
            .expectStatus().isCreated
            .expectBody(ResourceCreateResponse::class.java)
            .returnResult()
            .responseBody

        createdDto shouldNotBe null
        (createdDto!!.id > 0L) shouldBe true

        webTestClient.get().uri("/items")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(ItemListResponse::class.java)
            .hasSize(1)
            .contains(ItemListResponse(2L, Item.fixture.name, Item.fixture.price, Item.fixture.stockQuantity))
    }

    val Item.Companion.fixture
        get() = Item(
            name = "컨셉원 슬랙스 BLACK 30",
            price = 70_000,
            stockQuantity = 500,
            userId = 1
        )

    val ItemCreateRequest.Companion.fixture: ItemCreateRequest
        get() {

            val entity = Item.fixture

            return ItemCreateRequest(
                name = entity.name,
                price = entity.price,
                stockQuantity = entity.stockQuantity
            )
        }
}