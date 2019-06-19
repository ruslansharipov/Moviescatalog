package ru.sharipov.moviescatalog.di

import dagger.Module
import dagger.Provides
import ru.sharipov.moviescatalog.interaction.MoviesRepository
import ru.sharipov.moviescatalog.ui.MainFragmentPresenter

@Module
class PresentationModule {
    @Provides
    fun provideMainPresenter(repository: MoviesRepository): MainFragmentPresenter {
        return MainFragmentPresenter(repository)
    }
}