package db

import model.book.Book
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime
import utils.EventBus


object BookDao : LongIdTable("litnet_book") {
    var annotation = text("annotation").nullable()
    var author_name = text("author_name").nullable()
    var cover = text("cover").nullable()
    var createdAt = datetime("created_at")
    var pages = integer("pages")
    var title = text("title")
    var total_chr_length = long("total_chr_length")
    var url = text("url")
    var authorId = integer("author_id")
    var pubId = integer("pub_id").nullable()
    var pubName = text("pub_name").nullable()
    var type = text("type").nullable()
    var finishedAt = datetime("finished_at").nullable()
    var lastUpdate = datetime("last_update").nullable()
    var adultOnly = bool("adult_only")
    var bookActive = bool("book_active")
    var intro = bool("intro")
    var freeChapters = integer("freeChapters").default(-1)
    var rating = integer("rating")
    var likes = integer("likes")
    var rewards = integer("rewards")
    var views = long("views")
    var inLibraries = integer("in_libraries")
    var comments = integer("comments")
    var allowComments = bool("allow_comments")
    var price = double("price").default(0.0)
    var blocked = bool("blocked").default(false)
    var liked = bool("liked").default(false)
    var rewarded = bool("rewarded").default(false)
    var status = text("status").default("")
    var isPurchased = bool("is_purchased").default(false)
    var lang = text("lang").default("")
    var sellingFrozen = bool("selling_frozen").default(false)
    var lastTimeCheck = datetime("last_time_check").clientDefault { DateTime.now() }
}

class DBBook(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<DBBook>(BookDao)

    var annotation by BookDao.annotation
    var author_name by BookDao.author_name
    var cover by BookDao.cover
    var createdAt by BookDao.createdAt
    var pages by BookDao.pages
    var title by BookDao.title
    var total_chr_length by BookDao.total_chr_length
    var url by BookDao.url
    var authorId by BookDao.authorId
    var pubId by BookDao.pubId
    var pubName by BookDao.pubName
    var type by BookDao.type
    var finishedAt by BookDao.finishedAt
    var lastUpdate by BookDao.lastUpdate
    var adultOnly by BookDao.adultOnly
    var bookActive by BookDao.bookActive
    var intro by BookDao.intro
    var freeChapters by BookDao.freeChapters
    var rating by BookDao.rating
    var likes by BookDao.likes
    var rewards by BookDao.rewards
    var views by BookDao.views
    var inLibraries by BookDao.inLibraries
    var comments by BookDao.comments
    var allowComments by BookDao.allowComments
    var price by BookDao.price
    var blocked by BookDao.blocked
    var liked by BookDao.liked
    var rewarded by BookDao.rewarded
    var status by BookDao.status
    var isPurchased by BookDao.isPurchased
    var lang by BookDao.lang
    var sellingFrozen by BookDao.sellingFrozen
    var lastTimeCheck by BookDao.lastTimeCheck
}

fun Book.createOrUpdateDBBook() {
    transaction {
        val book = DBBook.find { BookDao.id eq id }.firstOrNull()
        if (book == null) {
            createDBBookInternal(this@createOrUpdateDBBook)
            return@transaction
        }

        if (book.lastUpdate?.millis ?: 0 < lastUpdate ?: 0) {
            EventBus.post(this@createOrUpdateDBBook)
            // - Notify user
            // - Notify DB
        }
        BookDao.update({ BookDao.id eq id }) {
            it[BookDao.lastTimeCheck] = DateTime.now()
        }
    }
}

private fun createDBBookInternal(book: Book) {
    BookDao.insert {
        it[id] = EntityID(book.id, BookDao)
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
    }
}

