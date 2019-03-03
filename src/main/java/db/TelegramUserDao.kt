package db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import org.joda.time.DateTime


object TelegramUserDao : LongIdTable("tuser") {
    val litNetToken = text("litnet_token").nullable()
    val litNetId = integer("litnet_id").nullable()
    val temporaryStorage = text("tmp_storage").nullable()
    val prevStage = integer("tmp_stage").nullable()
    val lastNotify = datetime("last_notify").clientDefault { DateTime.now() }
}

class TelegramUser(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TelegramUser>(TelegramUserDao)

    var litNetToken by TelegramUserDao.litNetToken
    var litNetId by TelegramUserDao.litNetId
    var temporaryStorage by TelegramUserDao.temporaryStorage
    var prevStage by TelegramUserDao.prevStage
    val lastNotify by TelegramUserDao.lastNotify
}

enum class TGUserStage(val id: Int) {
    WAIT_ENTER_LOGIN(1),
    WAIT_ENTER_PASSWORD(2),
    AUTH_IN_LITNET(3)
}