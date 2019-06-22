package ru.sharipov.moviescatalog.di

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.sharipov.moviescatalog.BuildConfig
import ru.sharipov.moviescatalog.interaction.network.MoviesApi
import ru.sharipov.moviescatalog.interaction.network.RequestInterceptor

@Module
class OkhttpModule {

    companion object {
        private const val TAG = "HTTP_LOG_TAG"
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MoviesApi.BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    fun provideOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Log.d(TAG, message) }
            .apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.BASIC
            }
    }

    @Provides
    fun provideTokenInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }
}