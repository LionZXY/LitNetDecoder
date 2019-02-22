package bot.cmd

import bot.LitNetBot
import core.RetrofitProvider
import db.TelegramUser
import org.telegram.telegrambots.meta.api.objects.Update

class CheckBookUpdate(val bot: LitNetBot) : IBotCommand {
    override fun matchCmd(cmd: String) = cmd.startsWith("/update")


    override fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
    }
}