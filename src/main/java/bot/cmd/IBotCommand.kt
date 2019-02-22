package bot.cmd

import core.RetrofitProvider
import db.TelegramUser
import org.telegram.telegrambots.meta.api.objects.Update

interface IBotCommand {
    fun matchCmd(cmd: String): Boolean
    fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider)
}