package bot.cmd

import bot.LitNetBot
import core.RetrofitProvider
import db.TelegramUser
import db.TelegramUserDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import sun.jvm.hotspot.debugger.AddressException
import javax.mail.internet.InternetAddress

class EmailSetter(val bot: LitNetBot) : IBotCommand {
    override fun matchCmd(cmd: String) = cmd.startsWith("/setEmail")

    override fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        val msg = upd.message.text
        val email = msg.substring(msg.indexOf(" ")).trim()
        var isValid = false
        try {
            val address = InternetAddress(email)
            address.validate()
            isValid = true
        } catch (e: AddressException) {
            println("Email $email not valid")
        }

        if (!isValid) {
            bot.execute(SendMessage(upd.message.chatId, "$email - не валидный email"))
            return
        }

        transaction {
            TelegramUserDao.update({ TelegramUserDao.id eq user.id.value }) {
                it[TelegramUserDao.email] = email
            }
        }

        bot.execute(SendMessage(upd.message.chatId, "$email для пользователя сохранен! Туда будут отправлятся обновления книг"))
    }

}