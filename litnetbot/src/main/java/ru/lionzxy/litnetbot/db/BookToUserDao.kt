package ru.lionzxy.litnetbot.db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable

object BookToUserDao : LongIdTable("book_to_user") {
    val book = long("bookid").entityId().references(BookDao.id)
    val user = long("userid").entityId().references(TelegramUserDao.id)

    init {
        uniqueIndex(book, user)
    }
}

class BookToUser(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TelegramUser>(TelegramUserDao)

    val book by BookToUserDao.book
    val user by BookToUserDao.user
}
