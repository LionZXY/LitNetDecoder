package models

data class BookChapter(var id: Int,
                       var title: String,
                       var pageCount: Int,
                       var text: String? = null) {
    constructor() : this(0, "", 0, null)
}