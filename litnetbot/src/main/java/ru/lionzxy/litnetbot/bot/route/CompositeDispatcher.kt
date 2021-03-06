package ru.lionzxy.litnetbot.bot.route

import org.telegram.telegrambots.meta.api.objects.Update
import ru.lionzxy.litnetbot.bot.LitNetBot
import ru.lionzxy.litnetbot.bot.cmd.*
import ru.lionzxy.litnetbot.core.RetrofitProvider
import ru.lionzxy.litnetbot.db.TelegramUser

class CompositeDispatcher(bot: LitNetBot) {
    // command to priority (greatest - important)
    val commands = listOf(
            CheckBookUpdate(bot) to 10,
            DownloadBook(bot) to 10,
            Logout(bot) to 10,
            EmailSetter(bot) to 10,
            UnknownMessage(bot) to 0).sortedByDescending { it.second }.map { it.first }
    val simpleCommands = listOf(StartMessage(bot), HelpMessage(bot))

    fun dispatchAndExecute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        val cmd = upd.message.text
        commands.find { it.matchCmd(cmd) }?.execute(upd, user, retrofitProvider)
    }

    fun dispatchAndExecute(upd: Update): Boolean {
        val cmd = upd.message.text
        val command = simpleCommands.find { it.matchCmd(cmd) } ?: return false
        command.execute(upd)
        return true
    }
}