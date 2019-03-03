package bot.cmd

import bot.LitNetBot
import core.RetrofitProvider
import db.TelegramUser
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class StartMessage(val bot: LitNetBot) : IBotCommand {
    override fun matchCmd(cmd: String) = cmd.startsWith("/start")

    override fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        val text = "Привет. С помощью этого бота можно сообщения об обновлении книг\n" +
                "Бот предоставляется как есть. Я не несу ответственности за вашего пользователя и прочее," +
                "так как делаю я его своими силами, не получая за это ни копейки (на некоммерческой основе)\n" +
                "На сервере не хранится логин-пароль, а лишь временный токен для доступа к litnet. Используется API для мобильных приложений\n" +
                "По запросу любому желающему даю доступ к исходникам\n\n" +
                "Помощь: /help"
        bot.execute(SendMessage(upd.message.chatId, text))
    }

}