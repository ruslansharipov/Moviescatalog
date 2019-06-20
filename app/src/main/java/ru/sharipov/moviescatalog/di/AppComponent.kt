package ru.sharipov.moviescatalog.di

import dagger.Component
import ru.sharipov.moviescatalog.ui.main_list.MainFragment

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