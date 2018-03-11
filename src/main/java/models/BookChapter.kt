package models

data class BookChapter(var id: Int,
                  var title: String,
                  var pageCount: Int) {
    constructor() : this(0, "", 0)
}