package io.philo.integration.query

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.philo.dto.QueryResponse
import io.philo.integration.support.IntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.ParameterizedTypeReference

class QueryIntegrationTest : IntegrationTest() {

    @BeforeEach
    override fun setUp() {
        super.setUp()
        sqlExecutor.execute("query-test-data.sql")
    }

    @Test
    fun `통계 정보 조회 및 검증`() {

        val responseBody = 통계_정보_조회()

        통계_정보_검증(responseBody)
    }

    private fun 통계_정보_조회() = webTestClient.get().uri("/query/user-statistics")

        .exchange()
        .expectStatus().isOk
        .expectBody(object : ParameterizedTypeReference<List<QueryResponse>>() {})
        .returnResult()
        .responseBody!!

    private fun 통계_정보_검증(responseBody: List<QueryResponse>) {

        responseBody shouldContainExactlyInAnyOrder listOf(
            QueryResponse(1, 1),
            QueryResponse(2, 2),
            QueryResponse(3, 0)
        )
    }
}