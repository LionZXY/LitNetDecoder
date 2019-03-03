package ru.lionzxy.litnetbot.utils

import com.adobe.dp.epub.io.DataSource
import java.io.InputStream

class InputDataSource(private val inputStream: InputStream): DataSource() {
    override fun getInputStream() = this.inputStream
}