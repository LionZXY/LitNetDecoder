package bot.route

import bot.LitNetBot
import bot.cmd.IBotCommand
import bot.cmd.CheckBookUpdate
import db.TelegramUser
import org.telegram.telegrambots.meta.api.objects.Update

class CompositeDispatcher(val bot: LitNetBot) {
    // command to priority (greatest - important)
    val commands = listOf(CheckBookUpdate(bot) to 10).sortedBy { it.second }.map{ it.first }


    fun dispatchAndExecute(upd: Update, user: TelegramUser) {
        val cmd = upd.message.text
        //commands.find { it.matchCmd() }
    }
}