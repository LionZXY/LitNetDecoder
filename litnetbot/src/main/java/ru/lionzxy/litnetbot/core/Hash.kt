package ru.lionzxy.litnetbot.core

import java.security.MessageDigest


object Hash {
    fun encode(str: String): String? {
        try {
            val instance = MessageDigest.getInstance("MD5")
            instance.update(str.toByteArray())
            val byteArray = instance.digest()
            val stringBuilder = StringBuilder()
            for (b in byteArray) {
                var toHexString = Integer.toHexString(b.toInt() and 255)
                while (toHexString.length < 2) {
                    val stringBuilder2 = StringBuilder()
                    stringBuilder2.append("0")
                    stringBuilder2.append(toHexString)
                    toHexString = stringBuilder2.toString()
                }
                stringBuilder.append(toHexString)
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}