package bot.cmd.helpers

import bot.LitNetBot
import core.RetrofitProvider
import db.*
import io.reactivex.Completable
import model.book.Book
import model.notify.NotifyObject
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import utils.EventBus


class UpdateHelper(val bot: LitNetBot) {
    init {
        EventBus.getObservable().subscribe {
            notifyDB(it)
            notifyUsers(it)
        }
    }

    public fun checkUpdate(provider: RetrofitProvider, user: TelegramUser): Boolean {
        val notices = provider.getNoticedApi().noticedGet().singleOrError().blockingGet()
                .filter { it.updateAt > user.lastNotify.millis }
        if (notices.isEmpty()) {
            return false
        }
        transaction {
            TelegramUserDao.update({ TelegramUserDao.id eq user.id }) {
                it[TelegramUserDao.lastNotify] = DateTime.now()
            }
        }

        notices.forEach { notifyAboutUpdate(bot, user, it) }

        checkUpdateLibrary(provider, user).blockingAwait()
        return true
    }

    public fun checkUpdateLibrary(provider: RetrofitProvider, user: TelegramUser): Completable {
        return provider.getLibraryApi().get()
                .flatMapIterable { it }
                .map { it.book }
                .map { book ->
                    book.createOrUpdateDBBook()
                    transaction {
                        BookToUserDao.insertIgnore {
                            it[BookToUserDao.user] = user.id
                            it[BookToUserDao.book] = EntityID(book.id, BookToUserDao)
                        }
                    }
                }
                .flatMapCompletable { Completable.complete() }
    }

    private fun notifyAboutUpdate(bot: LitNetBot, user: TelegramUser, notifyObject: NotifyObject) {
        val message = SendMessage()
        val redir = "<a href=\"${notifyObject.url}\">Перейти</a>"
        message.text = "${notifyObject.objectType.emoji}${notifyObject.objectType.description} \n\n ${notifyObject.text}\n$redir"
        message.enableHtml(true)
        message.chatId = user.id.value.toString()
        bot.execute(message)
    }

    private fun notifyUsers(book: Book) {
        //TODO
    }

    private fun notifyDB(book: Book) {
        transaction {
            BookDao.update({ BookDao.id eq book.id }) {
                it[annotation] = book.annotation
                it[author_name] = book.author_name
                it[cover] = book.cover
                it[createdAt] = DateTime(book.createdAt)
                it[pages] = book.pages
                it[title] = book.title
                it[total_chr_length] = book.total_chr_length
                it[url] = book.url
                it[authorId] = book.authorId
                it[pubId] = book.pubId
                it[pubName] = book.pubName
                it[type] = book.type
                it[finishedAt] = DateTime(book.finishedAt)
                it[lastUpdate] = DateTime(book.lastUpdate)
                it[adultOnly] = book.adultOnly
                it[bookActive] = book.bookActive
                it[intro] = book.intro
                it[freeChapters] = book.freeChapters
                it[rating] = book.rating
                it[likes] = book.likes
                it[rewards] = book.rewards
                it[views] = book.views
                it[inLibraries] = book.inLibraries
                it[comments] = book.comments
                it[allowComments] = book.allowComments
                it[price] = book.price
                it[blocked] = book.blocked
                it[liked] = book.liked
                it[rewarded] = book.rewarded
                it[status] = book.status
                it[isPurchased] = book.isPurchased
                it[lang] = book.lang
                it[sellingFrozen] = book.sellingFrozen
                it[lastTimeCheck] = DateTime.now()
            }
        }
    }
}