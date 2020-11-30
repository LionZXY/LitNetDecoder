package ru.lionzxy.litnetbot

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import ru.lionzxy.litnetbot.bot.LitNetBot
import ru.lionzxy.litnetbot.db.BookDao
import ru.lionzxy.litnetbot.db.BookToUserDao
import ru.lionzxy.litnetbot.db.TelegramUserDao
import ru.lionzxy.litnetbot.utils.Credentials
import ru.lionzxy.litnetbot.utils.CredentialsEnum
import java.sql.Connection.TRANSACTION_READ_UNCOMMITTED

fun main(args: Array<String>) {
    connectToDB()

    ApiContextInitializer.init()
    val telegramBotApi = TelegramBotsApi()
    TransactionManager.manager.defaultIsolationLevel = TRANSACTION_READ_UNCOMMITTED

    transaction {
        SchemaUtils.create(BookToUserDao, TelegramUserDao, BookDao)
        TelegramUserDao.update { it[TelegramUserDao.lastNotify] = DateTime(0L) }
    }
    telegramBotApi.registerBot(LitNetBot())
}

private fun connectToDB() {
    val url = Credentials.get(CredentialsEnum.SERVER_DATABASE_URL)
    val user = Credentials.get(CredentialsEnum.SERVER_DATABASE_USER)
    val password = Credentials.get(CredentialsEnum.SERVER_DATABASE_PASSWORD)
    val connectionPoolSize = 10

    val config = HikariConfig()
    config.driverClassName = "org.postgresql.Driver"
    config.jdbcUrl = url
    config.username = user
    config.password = password
    config.maximumPoolSize = connectionPoolSize

    Database.connect(HikariDataSource(config))
}
