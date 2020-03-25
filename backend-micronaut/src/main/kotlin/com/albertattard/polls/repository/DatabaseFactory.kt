package com.albertattard.polls.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
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
                SchemaUtils.createMissingTablesAndColumns(PollsTable)
            }
        }

    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
        config.driverClassName = "org.h2.Driver"
        config.username = "poll-user"
        config.password = "poll-password"
        /* Tests that need to access the DB will fail due to wrong password.  Need to re-enable this */
        // config.password = UUID.randomUUID().toString()
        return HikariDataSource(config)
    }
}
