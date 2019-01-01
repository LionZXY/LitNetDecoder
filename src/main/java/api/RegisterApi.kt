package api

import io.reactivex.Observable
import model.User
import retrofit2.http.GET
import model.AbstractUser
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @GET("registration/registration-by-device")
    fun signUpDevice(): Observable<User>

    @POST( "registration/registration-by-phone")
    fun signUpPhone(@Body abstractUser: AbstractUser): Observable<User>

    @POST("registration/registration-by-social")
    fun signUpSocial(@Body abstractUser: AbstractUser): Observable<User>
}