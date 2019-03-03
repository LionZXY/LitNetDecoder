package ru.lionzxy.litnetbot.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.lionzxy.litnetbot.model.AbstractUser
import ru.lionzxy.litnetbot.model.User

interface RegisterApi {
    @GET("registration/registration-by-device")
    fun signUpDevice(): Observable<User>

    @POST( "registration/registration-by-phone")
    fun signUpPhone(@Body abstractUser: AbstractUser): Observable<User>

    @POST("registration/registration-by-social")
    fun signUpSocial(@Body abstractUser: AbstractUser): Observable<User>
}