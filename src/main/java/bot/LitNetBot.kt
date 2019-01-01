package bot

import bot.auth.AuthProvider
import bot.route.CompositeDispatcher
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

class LitNetBot : TelegramLongPollingBot() {
    private val authProvider = AuthProvider(this)
    private val compositeDispatcher = CompositeDispatcher()

    override fun getBotUsername() = "LitNetBot"

    override fun getBotToken() = "686756892:AAE72ePX0gi6LytWgAc9chfxgEJNn7Sw1Pc"

    override fun onUpdateReceived(upd: Update?) {
        if (upd == null) {
            return
        }

        val user = authProvider.getCurrentUser(upd) ?: return
        compositeDispatcher.dispatchAndExecute(upd, user)
    }

}