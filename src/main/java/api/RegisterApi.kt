package api

import io.reactivex.Observable
import model.User
import retrofit2.http.GET



interface RegisterApi {
    @GET("registration/registration-by-device")
    fun signUpDevice(): Observable<User>
}