package ru.lionzxy.litnetbot.bot.cmd

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.lionzxy.litnetbot.bot.LitNetBot
import ru.lionzxy.litnetbot.core.RetrofitProvider
import ru.lionzxy.litnetbot.db.BookToUserDao
import ru.lionzxy.litnetbot.db.TelegramUser
import ru.lionzxy.litnetbot.db.TelegramUserDao

class Logout(val bot: LitNetBot) : IBotCommand {
    override fun matchCmd(cmd: String) = cmd.startsWith("/logout")

    override fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        transaction {
            BookToUserDao.deleteWhere { BookToUserDao.user eq user.id.value }
            TelegramUserDao.deleteWhere { TelegramUserDao.id eq user.id.value }
        }
        bot.execute(SendMessage(upd.message.chatId, "Информация о вас удалена"))
    }

}