package ru.lionzxy.litnetbot.utils

enum class CredentialsEnum {
    SERVER_DATABASE_URL,
    SERVER_DATABASE_USER,
    SERVER_DATABASE_PASSWORD,
    TG_USERNAME,
    TG_TOKEN
}

object Credentials {
    fun get(credName: CredentialsEnum): String {
        return System.getenv(credName.name)
    }
}
