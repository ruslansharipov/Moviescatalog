package ru.sharipov.moviescatalog.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.sharipov.moviescatalog.interaction.favourites.FavouriteDatabase
import ru.sharipov.moviescatalog.interaction.favourites.MoviesDao
import ru.sharipov.moviescatalog.interaction.repository.MoviesRepository
import ru.sharipov.moviescatalog.interaction.network.MoviesApi
import ru.sharipov.moviescatalog.interaction.repository.FavouritesRepository
import javax.inject.Singleton

@Module
class InteractionModule {

    companion object {
        private const val FAVOURITES = "favourites.db"
    }

    @Provides
    fun provideFaveRepository(moviesDao: MoviesDao): FavouritesRepository {
        return FavouritesRepository(moviesDao)
    }

    @Singleton
    @Provides
    fun provideFavouriteDatabase(context: Context): FavouriteDatabase {
        return Room.databaseBuilder(
            context,
            FavouriteDatabase::class.java,
            FAVOURITES
        ).build()
    }

    @Provides
    fun provideMoviesDao(database: FavouriteDatabase): MoviesDao {
        return database.moviesDao
    }

    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    fun provideMoviesRepository(moviesApi: MoviesApi): MoviesRepository {
        return MoviesRepository(moviesApi)
    }
}