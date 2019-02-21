package bot.auth

import bot.LitNetBot
import core.RetrofitProvider
import db.TelegramUser
import db.TelegramUserDao
import db.TGUserStage
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import kotlin.math.log

class AuthProvider(val bot: LitNetBot, val retrofitProvider: RetrofitProvider) {

    init {
        transaction {
            SchemaUtils.create(TelegramUserDao)
        }
    }

    fun getCurrentUser(upd: Update): TelegramUser? {
        var toExit: TelegramUser? = null
        val chatId = upd.message.chatId
        transaction {
            val telegramUser = TelegramUser.findById(chatId)

            if (upd.message.text.equals("Авторизоваться", true)) {
                TelegramUser.new (chatId) {
                    this.prevStage = TGUserStage.WAIT_ENTER_LOGIN.id
                }
                showAuthMessage(chatId)
                return@transaction
            }

            if (telegramUser == null) {
                showWelcomeSignUpMessage(chatId)
                return@transaction
            }

            if (telegramUser.prevStage == TGUserStage.WAIT_ENTER_LOGIN.id) {
                val login = upd.message.text
                TelegramUserDao.update ({ TelegramUserDao.id eq chatId }) {
                    it[TelegramUserDao.prevStage] = TGUserStage.WAIT_ENTER_PASSWORD.id
                    it[TelegramUserDao.temporaryStorage] = login
                }
                showPasswordMessage(chatId, login)
                return@transaction
            }

            if (telegramUser.prevStage == TGUserStage.WAIT_ENTER_PASSWORD.id) {
                val login = telegramUser.temporaryStorage ?: "Не введено"
                val password = upd.message.text
                val authMessageId = showProgressAuthMessage(chatId, login, password)

                TelegramUserDao.update({TelegramUserDao.id eq chatId}) {
                    it[TelegramUserDao.prevStage] = TGUserStage.AUTH_IN_LITNET.id
                }

                retrofitProvider.getUserApi().authByLogin(login, password).subscribe ({ user ->
                    TelegramUserDao.update ({ TelegramUserDao.id eq chatId }) { insStat ->
                        insStat[TelegramUserDao.litNetToken] = user.token
                    }
                    showAuthSucs(chatId, login, authMessageId)
                }, {
                    println(it)
                    showAuthFail(chatId, login, authMessageId)
                    TelegramUserDao.update ({ TelegramUserDao.id eq chatId }) { insStat ->
                        insStat[TelegramUserDao.prevStage] = TGUserStage.WAIT_ENTER_LOGIN.id
                    }
                    showAuthMessage(chatId)
                })
                return@transaction
            }

            if (telegramUser.litNetToken == null) {
                showAuthMessage(chatId)
                return@transaction
            }

            toExit = telegramUser
        }
        return toExit
    }

    private fun showAuthFail(chatId: Long, login: String, messageId: Int) {
        val message = EditMessageText()
        message.text = "Авторизация для пользователя $login не успешна. Проверьте логин и пароль. Если все правильно, обратитесь к @LionZXY"
        message.setChatId(chatId)
        message.messageId = messageId

        bot.execute(message)
    }

    private fun showAuthSucs(chatId: Long, login: String, messageId: Int) {
        val message = EditMessageText()
        message.text = "Авторизация успешна для пользователя $login!"
        message.messageId = messageId
        message.setChatId(chatId)

        bot.execute(message)
    }

    private fun showProgressAuthMessage(chatId: Long, login: String, password: String): Int {
        val message = SendMessage()
        message.text = "Авторизация для логина $login и пароля ${password.replace(".".toRegex(), "*")}..."
        message.setChatId(chatId)

        return bot.execute(message).messageId
    }

    private fun showPasswordMessage(chatId: Long, login: String) {
        val message = SendMessage()
        message.text = "Введите пароль для логина $login (потом можете удалить его из истории):"
        message.setChatId(chatId)
        bot.execute(message)
    }

    private fun showAuthMessage(chatId: Long) {
        val message = SendMessage()
        message.text = "Введите логин и пароль. Логин:"
        message.replyMarkup = ReplyKeyboardRemove()
        message.setChatId(chatId)
        bot.execute(message)
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