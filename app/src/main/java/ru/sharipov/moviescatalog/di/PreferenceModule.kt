package ru.sharipov.moviescatalog.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.sharipov.moviescatalog.interaction.favourites.FavesRepository

@Module
class PreferenceModule(private val application: Application) {

    companion object {
        private const val FAVOURITES = "FAVOURITES"
    }

    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences(FAVOURITES, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideFaveRepository(preferences: SharedPreferences): FavesRepository {
        return FavesRepository(preferences)
    }
}