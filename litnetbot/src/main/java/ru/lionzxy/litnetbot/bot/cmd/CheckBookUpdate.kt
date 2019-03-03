package ru.lionzxy.litnetbot.bot.cmd

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.lionzxy.litnetbot.bot.LitNetBot
import ru.lionzxy.litnetbot.bot.cmd.helpers.UpdateHelper
import ru.lionzxy.litnetbot.core.RetrofitProvider
import ru.lionzxy.litnetbot.db.TelegramUser

class CheckBookUpdate(val bot: LitNetBot) : IBotCommand {
    val updateHelper = UpdateHelper(bot)
    override fun matchCmd(cmd: String) = cmd.startsWith("/update")

    override fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        if (!updateHelper.checkUpdate(retrofitProvider, user)) {
            val message = SendMessage()
            message.text = "Обновлений нет ¯\\_(ツ)_/¯"
            message.chatId = user.id.value.toString()
            bot.execute(message)
        }
    }
}