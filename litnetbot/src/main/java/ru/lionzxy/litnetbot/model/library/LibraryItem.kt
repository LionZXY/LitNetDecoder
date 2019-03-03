package ru.lionzxy.litnetbot.model.library

import com.google.gson.annotations.SerializedName
import ru.lionzxy.litnetbot.model.book.Book

data class LibraryItem(
        @SerializedName("lib_info")
        var libInfo: LibraryItemInfo,
        @SerializedName("book")
        var book: Book
) {}