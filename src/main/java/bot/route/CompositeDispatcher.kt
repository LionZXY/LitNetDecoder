package bot.route

import bot.LitNetBot
import bot.cmd.CheckBookUpdate
import core.RetrofitProvider
import db.TelegramUser
import org.telegram.telegrambots.meta.api.objects.Update

class CompositeDispatcher(bot: LitNetBot) {
    // command to priority (greatest - importnt)
    val commands = listOf(CheckBookUpdate(bot) to 10).sortedBy { it.second }.map{ it.first }

    fun dispatchAndExecute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        val cmd = upd.message.text
        commands.find { it.matchCmd(cmd) }?.execute(upd, user, retrofitProvider)
    }
}