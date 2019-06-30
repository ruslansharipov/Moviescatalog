package ru.sharipov.moviescatalog.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.sharipov.moviescatalog.data.favourites_datasource.FavouriteDatabase
import ru.sharipov.moviescatalog.data.favourites_datasource.MoviesDao
import ru.sharipov.moviescatalog.data.repository.MoviesRepository
import ru.sharipov.moviescatalog.data.network.MoviesApi
import ru.sharipov.moviescatalog.data.repository.FavouritesRepository
import ru.sharipov.moviescatalog.domain.IFavouritesRepository
import ru.sharipov.moviescatalog.domain.IMoviesRepository
import javax.inject.Singleton

@Module
class DataModule {

    companion object {
        private const val FAVOURITES = "favourites.db"
    }

    @Provides
    fun provideFaveRepository(moviesDao: MoviesDao): IFavouritesRepository {
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
    fun provideMoviesRepository(moviesApi: MoviesApi): IMoviesRepository {
        return MoviesRepository(moviesApi)
    }
}