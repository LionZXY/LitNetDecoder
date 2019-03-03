package core

import okhttp3.Interceptor
import okhttp3.Response

const val deviceId = "351256985671943"
const val secretKey = "14a6579a984b3c6abecda6c2dfa83a64"

class LitNetInterceptorAuth : Interceptor {
    var userToken = ""

    fun setUser(userToken: String) {
        this.userToken = userToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrl = request.url().newBuilder().addEncodedQueryParameter("app", "android")
                .addEncodedQueryParameter("device_id", deviceId)
        val builder = StringBuilder()
        builder.append(deviceId)
        builder.append(secretKey)
        if (!userToken.isNullOrEmpty()) {
            builder.append(userToken)
            httpUrl.addEncodedQueryParameter("user_token", userToken)
        }
        httpUrl.addEncodedQueryParameter("sign", Hash.encode(builder.toString()))
                .addEncodedQueryParameter("version", 1174.toString())
                .addEncodedQueryParameter("lang_content", "ru")
                .addEncodedQueryParameter("lang_interface", "ru")
        return chain.proceed(request.newBuilder().url(httpUrl.build()).build())
    }
}