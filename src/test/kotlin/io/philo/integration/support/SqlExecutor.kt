package io.philo.integration.support

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Component
class SqlExecutor {

    @Autowired
    lateinit var databaseClient: DatabaseClient

    fun execute(sqlFileName: String) {
        val sql = readSql(sqlFileName)
        insertTestData(sql)
    }

    fun clearData() {
        val sql = readSql("clear-data-and-init-sequence.sql")
        insertTestData(sql)
    }

    private fun readSql(sqlFileName: String): String {

        val path: Path = Paths.get("src", "test", "resources", "sql", sqlFileName)
        return Files.readString(path)
    }

    private fun insertTestData(sql: String) {

        databaseClient.sql(sql).then().block()
    }
}