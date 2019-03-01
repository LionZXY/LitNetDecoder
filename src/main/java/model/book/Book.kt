package model.book

import com.google.gson.annotations.SerializedName

class Book(
        @SerializedName("annotation")
        var annotation: String,
        @SerializedName("author_name")
        var author_name: String,
        @SerializedName("genres")
        var genres: Array<Genre>,
        @SerializedName("cover")
        var cover: String,
        @SerializedName("created_at")
        var createdAt: Long,
        @SerializedName("pages")
        var pages: Int,
        @SerializedName("tags")
        var tags: Array<Tag>,
        @SerializedName("title")
        var title: String,
        @SerializedName("total_chr_length")
        var total_chr_length: Long,
        @SerializedName("url")
        var url: String,
        @SerializedName("id")
        var id: Long = 0,
        var chapters: ArrayList<BookChapter> = ArrayList(),
        @SerializedName("author_id")
        var authorId: Int = 0,
        @SerializedName("pub_id")
        var pubId: Int? = null,
        @SerializedName("pub_name")
        var pubName: String? = null,
        @SerializedName("type")
        var type: String? = null,
        @SerializedName("finished_at")
        var finishedAt: Long? = null,
        @SerializedName("last_update")
        var lastUpdate: Long? = null,
        @SerializedName("adult_only")
        var adultOnly: Boolean = false,
        @SerializedName("book_active")
        var bookActive: Boolean = true,
        @SerializedName("intro")
        var intro: Boolean = false,
        @SerializedName("free_chapters")
        var freeChapters: Int = -1,
        @SerializedName("rating")
        var rating: Int = 0,
        @SerializedName("likes")
        var likes: Int = 0,
        @SerializedName("rewards")
        var rewards: Int = 0,
        @SerializedName("views")
        var views: Long = 0,
        @SerializedName("in_libraries")
        var inLibraries: Int = 0,
        @SerializedName("comments")
        var comments: Int = 0,
        @SerializedName("allow_comments")
        var allowComments: Boolean = false,
        @SerializedName("price")
        var price: Double = 0.0,
        @SerializedName("blocked")
        var blocked: Boolean = false,
        @SerializedName("liked")
        var liked: Boolean = false,
        @SerializedName("rewarded")
        var rewarded: Boolean = false,
        @SerializedName("status")
        var status: String = "",
        @SerializedName("is_purchased")
        var isPurchased: Boolean = false,
        @SerializedName("lang")
        var lang: String = "",
        @SerializedName("selling_frozen")
        var sellingFrozen: Boolean = false) {
    constructor() : this("", "", emptyArray<Genre>(),
            "", 0L, 0, emptyArray<Tag>(),
            "", 0L, "")
}