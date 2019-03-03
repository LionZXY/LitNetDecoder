package ru.lionzxy.litnetbot.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.lionzxy.litnetbot.model.User

interface UserApi {
    @GET("user/find-by-token")
    fun findByToken(@Query("token") token: String): Observable<String>

    @GET("user/find-by-social-token")
    fun authBySocialToken(@Query("social_token") str: String): Observable<User>

    @GET("user/find-by-login")
    fun authByLogin(@Query("login") login: String, @Query("password") password: String): Observable<User>
}