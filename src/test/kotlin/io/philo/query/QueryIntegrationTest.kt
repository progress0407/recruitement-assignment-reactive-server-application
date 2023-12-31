package io.philo.query

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.philo.dto.QueryResponse
import io.philo.support.IntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.r2dbc.core.DatabaseClient
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

//@Sql(
//    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
//    scripts = ["classpath:sql/query-test-data.sql"]
//)
class QueryIntegrationTest : IntegrationTest() {

    @Autowired
    lateinit var databaseClient: DatabaseClient

    @BeforeEach
    fun setUp() {
        val sql = readSql("query-test-data.sql")
        insertTestData(sql)
    }

    private fun insertTestData(sql: String) {
        databaseClient.sql(sql).then().block()
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

    private fun readSql(sqlFileName: String): String {

        val path: Path = Paths.get("src", "test", "resources", "sql", sqlFileName)
        return Files.readString(path)
    }
}