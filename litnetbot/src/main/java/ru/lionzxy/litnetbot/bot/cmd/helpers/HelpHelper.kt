package ru.lionzxy.litnetbot.bot.cmd.helpers

object HelpHelper {
    val HELP_TEXT = """/help - справка
        |/logout - удалить все данные из базы данных бота
        |/start - информация для новых пользователей
        |/download %ссылка на книгу% - скачать книгу по ссылке
        |/update - проверить обновления пользователя (автообновление работает раз в час [WIP])
        |/setEmail - назначить почту для пользователя
    """.trimMargin()
}