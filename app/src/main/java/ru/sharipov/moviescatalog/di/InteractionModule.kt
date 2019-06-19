package ru.sharipov.moviescatalog.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.sharipov.moviescatalog.interaction.MoviesRepository
import ru.sharipov.moviescatalog.interaction.network.MoviesApi

@Module
class InteractionModule {

    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    fun provideMoviesRepository(moviesApi: MoviesApi): MoviesRepository {
        return MoviesRepository(moviesApi)
    }
}