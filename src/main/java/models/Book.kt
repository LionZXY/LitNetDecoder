package models

data class Book(var annotation: String,
                var author_name: String,
                var genres: Array<Genre>,
                var cover: String,
                var created_at: Long,
                var pages: Int,
                var tags: Array<Tag>,
                var title: String,
                var total_chr_length: Long,
                var url: String,
                var id: Int = 0,
                var chapters: List<BookChapter> = emptyList()) {
    constructor() : this("", "", emptyArray<Genre>(),
            "", 0L, 0, emptyArray<Tag>(),
            "", 0L, "")
}