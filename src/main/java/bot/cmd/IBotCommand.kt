package bot.cmd

interface IBotCommand {
    fun matchCmd(cmd: String): Boolean
}