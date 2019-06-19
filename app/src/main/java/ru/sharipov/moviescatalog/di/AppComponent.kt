package ru.sharipov.moviescatalog.di

import android.app.Application
import dagger.Component
import ru.sharipov.moviescatalog.ui.MainFragment

@Component(
    modules = [
        OkhttpModule::class,
        InteractionModule::class,
        PreferenceModule::class,
        PresentationModule::class
    ]
)
interface AppComponent {
    fun inject(target: MainFragment)
}