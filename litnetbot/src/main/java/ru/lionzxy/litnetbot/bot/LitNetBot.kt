package ru.lionzxy.litnetbot.bot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.lionzxy.litnetbot.bot.auth.AuthProvider
import ru.lionzxy.litnetbot.bot.route.CompositeDispatcher
import ru.lionzxy.litnetbot.core.RetrofitProvider
import ru.lionzxy.litnetbot.utils.Credentials
import ru.lionzxy.litnetbot.utils.CredentialsEnum

class LitNetBot : TelegramLongPollingBot() {
    private val authProvider = AuthProvider(this)
    private val compositeDispatcher = CompositeDispatcher(this)

    override fun getBotUsername() = Credentials.get(CredentialsEnum.TG_USERNAME)

    override fun getBotToken() = Credentials.get(CredentialsEnum.TG_TOKEN)

    override fun onUpdateReceived(upd: Update?) {
        if (upd == null) {
            return
        }

        try {
            internalOnUpdateReceived(upd)
        } catch (e: Exception) {
            e.printStackTrace()
            val message = SendMessage(upd.message.chatId, "Что-то пошло не так... Ошибка отправленна разработчику")
            execute(message)
        }
    }

    private fun internalOnUpdateReceived(upd: Update) {
        if (compositeDispatcher.dispatchAndExecute(upd)) {
            return
        }

        val retrofitProvider = RetrofitProvider()
        val user = authProvider.getCurrentUser(upd, retrofitProvider) ?: return
        compositeDispatcher.dispatchAndExecute(upd, user, retrofitProvider)
    }

}
