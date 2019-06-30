package ru.sharipov.moviescatalog.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class RequestInterceptor : Interceptor {
    companion object {
        private const val TOKEN = "6ccd72a2a8fc239b13f209408fc31c33"
        private const val API_KEY = "api_key"
        private const val LANGUAGE = "language"
        private const val REGION = "region"
    }

    private val locale = Locale.getDefault()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url()
            .newBuilder()
            .addQueryParameter(REGION, locale.country)
            .addQueryParameter(LANGUAGE, locale.language)
            .addQueryParameter(API_KEY, TOKEN)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}