package api

import io.reactivex.Observable
import model.book.Book
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {
    @GET("book/get/{id}")
    fun getBookById(@Path("id") id: Int): Observable<Book>
}