package ru.sharipov.moviescatalog.di

import dagger.Module
import dagger.Provides
import ru.sharipov.moviescatalog.domain.IFavouritesRepository
import ru.sharipov.moviescatalog.domain.IMoviesRepository
import ru.sharipov.moviescatalog.ui.main_list.MainFragmentPresenter

@Module
class PresentationModule {
    @Provides
    fun provideMainPresenter(
        moviesRepository: IMoviesRepository,
        favouritesRepository: IFavouritesRepository
    ): MainFragmentPresenter {
        return MainFragmentPresenter(moviesRepository, favouritesRepository)
    }
}