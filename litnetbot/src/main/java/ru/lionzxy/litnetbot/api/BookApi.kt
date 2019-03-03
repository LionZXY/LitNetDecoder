package ru.lionzxy.litnetbot.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.lionzxy.litnetbot.model.book.Book
import ru.lionzxy.litnetbot.model.book.BookChapter
import ru.lionzxy.litnetbot.model.book.BookChapterText
import ru.lionzxy.litnetbot.model.library.BookChaptersWrap

interface BookApi {
    @GET("book/get/{id}")
    fun getBookById(@Path("id") id: Int): Observable<Book>

    @GET("book/contents")
    fun getContentsById(@Query("bookId") id: Int): Observable<List<BookChapter>>

    @GET("book/contents-by-ids")
    fun getContentsByIds(@Query("book_ids[]") ids: List<Long>): Observable<List<BookChaptersWrap>>

    @GET("book/get-chapters-texts")
    fun getCahpters(@Query("chapter_ids[]") ids: List<Int>): Observable<List<BookChapterText>>
}