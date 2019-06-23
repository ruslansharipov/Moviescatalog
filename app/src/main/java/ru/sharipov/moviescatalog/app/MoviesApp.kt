package ru.sharipov.moviescatalog.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.sharipov.moviescatalog.di.AppComponent
import ru.sharipov.moviescatalog.di.ApplicationModule
import ru.sharipov.moviescatalog.di.DaggerAppComponent

class MoviesApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

    }
}