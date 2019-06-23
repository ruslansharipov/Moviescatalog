package ru.sharipov.moviescatalog.di

import dagger.Module
import dagger.Provides
import ru.sharipov.moviescatalog.interaction.repository.MoviesRepository
import ru.sharipov.moviescatalog.interaction.repository.FavouritesRepository
import ru.sharipov.moviescatalog.ui.main_list.MainFragmentPresenter

@Module
class PresentationModule {
    @Provides
    fun provideMainPresenter(
        moviesRepository: MoviesRepository,
        favouritesRepository: FavouritesRepository
    ): MainFragmentPresenter {
        return MainFragmentPresenter(moviesRepository, favouritesRepository)
    }
}