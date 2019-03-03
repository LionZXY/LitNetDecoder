package ru.lionzxy.litnetbot.bot.cmd

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.lionzxy.litnetbot.bot.LitNetBot
import ru.lionzxy.litnetbot.bot.cmd.helpers.HelpHelper
import ru.lionzxy.litnetbot.core.RetrofitProvider
import ru.lionzxy.litnetbot.db.TelegramUser

class UnknownMessage(val bot: LitNetBot) : IBotCommand {
    override fun matchCmd(cmd: String) = true

    override fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        val text = "Не найдена такая команда. Для просмотра доступных комманд в любое время используйте /help\n\n${HelpHelper.HELP_TEXT}"
        bot.execute(SendMessage(upd.message.chatId, text))
    }

}