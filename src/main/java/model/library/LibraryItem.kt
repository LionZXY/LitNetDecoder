package model.library

import com.google.gson.annotations.SerializedName
import model.book.Book

data class LibraryItem(
        @SerializedName("lib_info")
        var libInfo: LibraryItemInfo,
        @SerializedName("book")
        var book: Book
) {}