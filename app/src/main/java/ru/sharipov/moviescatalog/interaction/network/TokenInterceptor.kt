package ru.sharipov.moviescatalog.interaction.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    companion object {
        private const val TOKEN = "6ccd72a2a8fc239b13f209408fc31c33"
        private const val API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url()
            .newBuilder()
            .addQueryParameter(API_KEY,TOKEN)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}