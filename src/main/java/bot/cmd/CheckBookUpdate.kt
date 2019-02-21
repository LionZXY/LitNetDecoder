package bot.cmd

import bot.LitNetBot

class CheckBookUpdate(val bot: LitNetBot): IBotCommand {
    override fun matchCmd(cmd: String) = cmd.startsWith("/update")

}