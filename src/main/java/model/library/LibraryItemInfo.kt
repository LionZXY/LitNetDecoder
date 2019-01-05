package model.library

import com.google.gson.annotations.SerializedName

data class LibraryItemInfo(
        @SerializedName("add_date")
        var addDate: Long,
        @SerializedName("last_chr_count")
        var lastCharCount: Long,
        @SerializedName("type")
        var type: Int)