package ru.sharipov.moviescatalog.di

import dagger.Component
import ru.sharipov.moviescatalog.ui.main_list.MainFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        OkhttpModule::class,
        InteractionModule::class,
        PresentationModule::class
    ]
)
interface AppComponent {
    fun inject(target: MainFragment)
}