package model.book

import com.google.gson.annotations.SerializedName

class BookChapterText(
        @SerializedName("id")
        var id: Int,
        @SerializedName("access")
        var access: Boolean,
        @SerializedName("text")
        var text: String)