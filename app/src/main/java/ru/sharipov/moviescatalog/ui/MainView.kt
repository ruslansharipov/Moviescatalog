package ru.sharipov.moviescatalog.ui

import moxy.MvpView
import ru.sharipov.moviescatalog.domain.MovieItem

interface MainView: MvpView {
    fun showMovies(movies: List<MovieItem>)
    fun showListProgress()
    fun hideListProgress()
    fun showSearchProgress()
    fun hideSearchProgress()
}
