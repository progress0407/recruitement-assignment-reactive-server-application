package io.philo.item

import io.philo.AcceptanceTest
import io.philo.dto.ResourceCreateResponse
import io.philo.shop.dto.ItemCreateRequest
import io.philo.shop.dto.ItemListResponse
import io.philo.shop.entity.Item
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemIntegrationTest : AcceptanceTest(){

    @Autowired
    lateinit var  webClient: WebTestClient

    @Test
    fun `재고 등록 및 조회`() {

        // given
        val requestBody = ItemCreateRequest.fixture

        // when
        webClient.post().uri("/items")
            .contentType(APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isCreated
            .expectBody(ResourceCreateResponse::class.java)
            .isEqualTo(ResourceCreateResponse(1L))

        webClient.get().uri("/items")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(ItemListResponse::class.java)
            .hasSize(1)
            .contains(ItemListResponse(2L, Item.fixture.name, Item.fixture.price, Item.fixture.stockQuantity))

    }

    val ItemCreateRequest.Companion.fixture: ItemCreateRequest
        get() {

            val entity = Item.fixture

            return ItemCreateRequest(
                name = entity.name,
                price = entity.price,
                stockQuantity = entity.stockQuantity
            )
        }

    val Item.Companion.fixture
        get() = Item(name = "컨셉원 슬랙스 BLACK 30",
            price = 70_000,
            stockQuantity = 500)
}