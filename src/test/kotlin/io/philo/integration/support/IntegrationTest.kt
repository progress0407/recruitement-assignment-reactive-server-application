package io.philo.integration.support

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.philo.dto.ResourceCreateResponse
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

    protected fun 생성된_자원_검증(createdDto: ResourceCreateResponse?) {

        createdDto shouldNotBe null
        (createdDto!!.id > 0L) shouldBe true
    }
}