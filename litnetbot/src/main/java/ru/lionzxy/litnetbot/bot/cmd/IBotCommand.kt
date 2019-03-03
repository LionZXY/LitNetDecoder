package ru.lionzxy.litnetbot.bot.cmd

import org.telegram.telegrambots.meta.api.objects.Update
import ru.lionzxy.litnetbot.core.RetrofitProvider
import ru.lionzxy.litnetbot.db.TelegramUser

interface IBotCommand {
    fun matchCmd(cmd: String): Boolean
    fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider)
}