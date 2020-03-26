package com.albertattard.polls.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import java.util.UUID
import javax.sql.DataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Factory
class DatabaseFactory {

    @Bean
    fun database(dataSource: DataSource) =
        Database.connect(dataSource).apply {
            transaction(this) {
                SchemaUtils.createMissingTablesAndColumns(PollsTable, QuestionsTable, PossibleAnswersTable)
            }
        }

    @Bean
    fun dataSource(): DataSource =
        HikariConfig().apply {
            /* Use a random database name */
            jdbcUrl = "jdbc:h2:mem:${UUID.randomUUID()};DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
            driverClassName = "org.h2.Driver"
            username = "poll-user"
            /* Use a random database password */
            password = "${UUID.randomUUID()}"
        }.let {
            HikariDataSource(it)
        }
}
