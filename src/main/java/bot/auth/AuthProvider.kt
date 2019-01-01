package bot.auth

import bot.LitNetBot
import db.TelegramUser
import db.TelegramUserDao
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

class AuthProvider(val bot: LitNetBot) {

    init {
        transaction {
            SchemaUtils.create(TelegramUserDao)
        }
    }

    fun getCurrentUser(upd: Update): TelegramUser? {
        var toExit: TelegramUser? = null
        transaction {
            val telegramUser = TelegramUser.findById(upd.message.chatId)
            if (telegramUser == null) {
                showWelcomeSignUpMessage(upd.message.chatId)
            }
        }
        return toExit
    }

    private fun showWelcomeSignUpMessage(chatId: Long) {
        val message = SendMessage()
        message.text = "Для того чтобы пользоваться ботом требуется авторизация. \n\n" +
                "Напоминаю, что бот неофициальный. Из-за этого приходится авторизоваться не через токен на сайте.\n" +
                "Пока регистрация доступна только через логин-пароль. По любым вопросам и пожеланиям пишите @LionZXY\n" +
                "Бот не хранит ваш логин и пароль, они используются лишь один раз для авторизации!\n\n  " +
                "Вы всегда можете удалить свои данные и пользовательскую информацию, введя /logout"
        val keyboard = ReplyKeyboardMarkup()
        val keyboardRow = KeyboardRow()
        val button = InlineKeyboardButton("Авторизоваться")
        button.callbackData = "auth"
        keyboardRow.add("Авторизоваться")
        keyboard.keyboard = listOf(keyboardRow)


        message.setChatId(chatId)
        message.replyMarkup = keyboard
        bot.execute(message)
    }
}