package ru.lionzxy.litnetbot.api

import io.reactivex.Observable
import retrofit2.http.GET
import ru.lionzxy.litnetbot.model.library.LibraryItem

interface LibraryApi {
    @GET("library/get")
    fun get(): Observable<List<LibraryItem>>
}