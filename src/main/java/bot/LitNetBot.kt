package bot

import bot.auth.AuthProvider
import bot.route.CompositeDispatcher
import core.RetrofitProvider
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove

class LitNetBot : TelegramLongPollingBot() {
    private val retrofitProvider = RetrofitProvider()
    private val authProvider = AuthProvider(this, retrofitProvider)
    private val compositeDispatcher = CompositeDispatcher(this)

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