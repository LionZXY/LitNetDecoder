package ru.lionzxy.litnetbot.bot.cmd

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.lionzxy.litnetbot.bot.LitNetBot
import ru.lionzxy.litnetbot.bot.cmd.helpers.HelpHelper
import ru.lionzxy.litnetbot.core.RetrofitProvider
import ru.lionzxy.litnetbot.db.TelegramUser

class HelpMessage(val bot: LitNetBot) : IBotCommand {
    override fun matchCmd(cmd: String) = cmd.startsWith("/help")

    override fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        bot.execute(SendMessage(upd.message.chatId, HelpHelper.HELP_TEXT))
    }

}