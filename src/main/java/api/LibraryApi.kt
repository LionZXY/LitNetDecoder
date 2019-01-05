package api

import io.reactivex.Observable
import model.library.LibraryItem
import retrofit2.http.GET

interface LibraryApi {
    @GET("library/get")
    fun get(): Observable<List<LibraryItem>>
}