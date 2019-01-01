import bot.LitNetBot
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import java.sql.Connection.TRANSACTION_READ_UNCOMMITTED
import java.sql.DriverManager

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    val telegramBotApi = TelegramBotsApi()
    Database.connect("jdbc:sqlite:litnetbot.db", driver = "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = TRANSACTION_READ_UNCOMMITTED
    telegramBotApi.registerBot(LitNetBot())
}