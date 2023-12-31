package io.philo.integration.support

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var sqlExecutor: SqlExecutor

    @BeforeEach
    fun setUp() {
        sqlExecutor.clearData()
    }
}