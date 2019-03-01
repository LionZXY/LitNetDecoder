package model.library

import com.google.gson.annotations.SerializedName
import model.book.BookChapter

data class BookChaptersWrap(
        @SerializedName("book_id")
        var bookId: Int,
        @SerializedName("contents")
        var contents: List<BookChapter>)