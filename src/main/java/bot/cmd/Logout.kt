package bot.cmd

import bot.LitNetBot
import core.RetrofitProvider
import db.BookToUserDao
import db.TelegramUser
import db.TelegramUserDao
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

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