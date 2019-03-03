package bot

import bot.auth.AuthProvider
import bot.route.CompositeDispatcher
import core.RetrofitProvider
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class LitNetBot : TelegramLongPollingBot() {
    private val retrofitProvider = RetrofitProvider()
    private val authProvider = AuthProvider(this)
    private val compositeDispatcher = CompositeDispatcher(this)

    override fun getBotUsername() = "LitNetBot"

    override fun getBotToken() = "686756892:AAE72ePX0gi6LytWgAc9chfxgEJNn7Sw1Pc"

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
        val retrofitProvider = RetrofitProvider()
        val user = authProvider.getCurrentUser(upd, retrofitProvider) ?: return
        compositeDispatcher.dispatchAndExecute(upd, user, retrofitProvider)
    }

}