package bot.cmd

import bot.LitNetBot
import bot.cmd.helpers.DownloadHelper
import core.RetrofitProvider
import db.TelegramUser
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update

class DownloadBook(val bot: LitNetBot) : IBotCommand {
    val downloadHelper = DownloadHelper()
    override fun matchCmd(cmd: String) = cmd.startsWith("/download")

    override fun execute(upd: Update, user: TelegramUser, retrofitProvider: RetrofitProvider) {
        val message = upd.message.text
        val indexOf = message.indexOf("litnet.com/ru/book/")
        if (indexOf < 1) {
            bot.execute(SendMessage(upd.message.chatId, "Не могу найти ссылку на скачивание книги. Мне нужна комманда вида:\n" +
                    "/download https://litnet.com/ru/book/disgardium-2-inicial-spyashchih-b102114"))
            return
        }

        val bookId = message.substring(message.indexOf("-b", indexOf) + 2)
        val messageId = bot.execute(SendMessage(upd.message.chatId, "Скачивание книги с id $bookId ...")).messageId
        val book = retrofitProvider.getBookApi().getBookById(bookId.toInt()).singleOrError().blockingGet()
        val bookFile = downloadHelper.downloadBook(book, retrofitProvider, user)

        val sendDocument = SendDocument()
        sendDocument.document = InputFile(bookFile, bookFile.name)
        sendDocument.setChatId(upd.message.chatId)
        sendDocument.caption = book.title

        val deleteDownloadMessage = DeleteMessage()
        deleteDownloadMessage.setChatId(upd.message.chatId)
        deleteDownloadMessage.messageId = messageId

        bot.execute(sendDocument)
        bot.execute(deleteDownloadMessage)
    }

}