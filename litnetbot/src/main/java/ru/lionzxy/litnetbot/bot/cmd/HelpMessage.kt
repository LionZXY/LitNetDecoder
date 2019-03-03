package ru.lionzxy.litnetbot.bot.cmd

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.lionzxy.litnetbot.bot.LitNetBot
import ru.lionzxy.litnetbot.bot.cmd.helpers.HelpHelper

class HelpMessage(val bot: LitNetBot) : ISimpleBotCommand {
    override fun matchCmd(cmd: String) = cmd.startsWith("/help")

    override fun execute(upd: Update) {
        bot.execute(SendMessage(upd.message.chatId, HelpHelper.HELP_TEXT))
    }

}