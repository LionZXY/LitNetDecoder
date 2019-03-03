package ru.lionzxy.litnetbot.bot.cmd

import org.telegram.telegrambots.meta.api.objects.Update

interface ISimpleBotCommand {
    fun matchCmd(cmd: String): Boolean
    fun execute(upd: Update)
}