package db

import org.jetbrains.exposed.dao.*


object TelegramUserDao : LongIdTable("tuser") {
    val litNetToken = text("litnet_token").nullable()
    val litNetId = integer("litnet_id").nullable()
    val temporaryStorage = text("tmp_storage").nullable()
    val prevStage = integer("tmp_stage").nullable()
}

class TelegramUser(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TelegramUser>(TelegramUserDao)

    var litNetToken by TelegramUserDao.litNetToken
    var litNetId by TelegramUserDao.litNetId
    var temporaryStorage by TelegramUserDao.temporaryStorage
    var prevStage by TelegramUserDao.prevStage
}