package bot.route

import bot.LitNetBot
import bot.cmd.*
import core.RetrofitProvider
import db.TelegramUser
import org.telegram.telegrambots.meta.api.objects.Update

class CompositeDispatcher(bot: LitNetBot) {
    // command to priority (greatest - important)
    val commands = listOf(
            CheckBookUpdate(bot) to 10,
            DownloadBook(bot) to 10,
            HelpMessage(bot) to 10,
            Logout(bot) to 10,
            EmailSetter(bot) to 10,
            StartMessage(bot) to 9,
            UnknownMessage(bot) to 0).sortedByDescending { it.second }.map { it.first }

    fun dispatchAndExecute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        val cmd = upd.message.text
        commands.find { it.matchCmd(cmd) }?.execute(upd, user, retrofitProvider)
    }
}