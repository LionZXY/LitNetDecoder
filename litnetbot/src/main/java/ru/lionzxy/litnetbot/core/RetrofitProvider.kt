package ru.lionzxy.litnetbot.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.lionzxy.litnetbot.api.*

class RetrofitProvider {
    private val authInterceptor = LitNetInterceptorAuth()
    private val retrofit = Retrofit.Builder()
            .client(getOkHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://sapi.litnet.com/v1/")
            .build()

    fun getRegisterApi(): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }

    fun getUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    fun getLibraryApi(): LibraryApi {
        return retrofit.create(LibraryApi::class.java)
    }

    fun getBookApi(): BookApi {
        return retrofit.create(BookApi::class.java)
    }

    fun getNoticedApi(): NoticedApi {
        return retrofit.create(NoticedApi::class.java)
    }

    fun setUserToken(token: String) {
        authInterceptor.setUser(token)
    }

    private fun getOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .build()
    }
}