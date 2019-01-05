package core

import api.BookApi
import api.LibraryApi
import api.RegisterApi
import api.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
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

    private fun getOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(LitNetInterceptorAuth)
                .addInterceptor(logging)
                .build()
    }
}