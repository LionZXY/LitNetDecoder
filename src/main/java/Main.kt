import bot.LitNetBot
import db.BookDao
import db.BookToUserDao
import db.TelegramUserDao
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import java.sql.Connection.TRANSACTION_READ_UNCOMMITTED

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    val telegramBotApi = TelegramBotsApi()
    Database.connect("jdbc:sqlite:litnetbot.db", driver = "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = TRANSACTION_READ_UNCOMMITTED

    transaction {
        SchemaUtils.create(BookToUserDao, TelegramUserDao, BookDao)
        TelegramUserDao.update { it[TelegramUserDao.lastNotify] = DateTime(0L) }
    }
    telegramBotApi.registerBot(LitNetBot())
}