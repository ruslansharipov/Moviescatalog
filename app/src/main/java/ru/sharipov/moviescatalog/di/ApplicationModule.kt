package ru.sharipov.moviescatalog.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val application: Application
) {
    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }
}