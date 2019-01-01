package api

import io.reactivex.Observable
import model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("user/find-by-token")
    fun findByToken(@Query("token") token: String): Observable<String>

    @GET("user/find-by-social-token")
    fun authBySocialToken(@Query("social_token") str: String): Observable<User>

    @GET("user/find-by-login")
    fun authByLogin(@Query("login") login: String, @Query("password") password: String): Observable<User>
}