package bot.cmd.helpers

import bot.LitNetBot
import db.BookDao
import model.book.Book
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime
import utils.EventBus


class UpdateHelper(bot: LitNetBot) {
    init {
        EventBus.getObservable().subscribe {
            notifyDB(it)
            notifyUser(it)
        }
    }

    private fun notifyUser(book: Book) {
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