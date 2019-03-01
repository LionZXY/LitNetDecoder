package utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayInputStream
import java.nio.charset.Charset
import java.util.*
import java.util.zip.GZIPInputStream
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object LitNetDecode {
    val gson = Gson()
    fun encrypt(cryptString: String): String {

        val instance = Cipher.getInstance("AES/CBC/PKCS5Padding")
        instance.init(2, SecretKeySpec("14a6579a984b3c6abecda6c2dfa83a64".toByteArray(charset("UTF-8")), "AES"),
                IvParameterSpec(cryptString.toByteArray(Charset.forName("UTF-8")), 0, 16))
        val doFinal = instance.doFinal(Base64.getDecoder().decode(cryptString))
        val bArr = Arrays.copyOfRange(doFinal, 16, doFinal.size)

        return String(bArr, Charset.forName("UTF-8"))
    }

    fun parseAndEncode(cryptString: String?): String? {
        if (cryptString == null) {
            return null
        }
        val json = encrypt(cryptString)
        val type = object : TypeToken<List<String>>() {}.type
        val lines = gson.fromJson<List<String>>(json, type)
        return lines.joinToString("\n")
    }
}