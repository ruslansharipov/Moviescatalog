package ru.sharipov.moviescatalog.app

import android.app.Application
import ru.sharipov.moviescatalog.di.AppComponent
import ru.sharipov.moviescatalog.di.DaggerAppComponent
import ru.sharipov.moviescatalog.di.PreferenceModule
import ru.sharipov.moviescatalog.di.PresentationModule

class MoviesApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .preferenceModule(PreferenceModule(this))
            .build()

    }
}