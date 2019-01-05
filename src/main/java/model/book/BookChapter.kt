package model.book

import com.google.gson.annotations.SerializedName

data class BookChapter(
        @SerializedName("id")
        var id: Int,
                       var title: String,
                       var pageCount: Int,
                       var text: String? = null) {
    constructor() : this(0, "", 0, null)
}