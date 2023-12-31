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

        val responseBody = webTestClient.get().uri("/query/user-statistics")
            .exchange()
            .expectStatus().isOk
            .expectBody(object : ParameterizedTypeReference<List<QueryResponse>>() {} )
            .returnResult()
            .responseBody!!

        responseBody shouldContainExactlyInAnyOrder listOf(
            QueryResponse(1, 1),
            QueryResponse(2, 2),
            QueryResponse(3, 0)
        )
    }


}