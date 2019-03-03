package ru.lionzxy.litnetbot.model.library

import com.google.gson.annotations.SerializedName
import ru.lionzxy.litnetbot.model.book.BookChapter

data class BookChaptersWrap(
        @SerializedName("book_id")
        var bookId: Int,
        @SerializedName("contents")
        var contents: List<BookChapter>)