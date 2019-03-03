package bot.cmd

import bot.LitNetBot
import bot.cmd.helpers.HelpHelper
import core.RetrofitProvider
import db.TelegramUser
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class HelpMessage(val bot: LitNetBot) : IBotCommand {
    override fun matchCmd(cmd: String) = cmd.startsWith("/help")

    override fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        bot.execute(SendMessage(upd.message.chatId, HelpHelper.HELP_TEXT))
    }

}