package api

import io.reactivex.Observable
import model.book.Book
import model.book.BookChapter
import model.book.BookChapterText
import model.library.BookChaptersWrap
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @GET("book/get/{id}")
    fun getBookById(@Path("id") id: Int): Observable<Book>

    @GET("book/contents")
    fun getContentsById(@Query("bookId") id: Int): Observable<List<BookChapter>>

    @GET("book/contents-by-ids")
    fun getContentsByIds(@Query("book_ids[]") ids: List<Int>): Observable<List<BookChaptersWrap>>

    @GET("book/get-chapters-texts")
    fun getCahpters(@Query("chapter_ids[]") ids: List<Int>): Observable<List<BookChapterText>>
}