package model.book

import com.google.gson.annotations.SerializedName

data class BookChapter(
        @SerializedName("id")
        var id: Int,
        @SerializedName("title")
        var title: String,
        var pageCount: Int,
        var text: String? = null,
        @SerializedName("book_id")
        var bookId: Int = 0,
        @SerializedName("create_date")
        var createDate: Long = 0,
        @SerializedName("last_update")
        var lastUpdate: Long = 0,
        @SerializedName("active")
        var active: Boolean = false,
        @SerializedName("chr_length")
        var chr_length: Int = 0,
        @SerializedName("priority")
        var priority: Int = 0,
        @SerializedName("access")
        var access: Boolean = false) {
    constructor() : this(0, "", 0, null)
}