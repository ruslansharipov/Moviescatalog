package ru.sharipov.moviescatalog.ui.main_list

import moxy.MvpView
import ru.sharipov.moviescatalog.domain.MovieItem

interface MainView: MvpView {
    fun onListLoading()
    fun onSearchLoading()
    fun onEmptyList()
    fun onError()
    fun onListLoaded(movies: List<MovieItem>)
    fun hideSwipeRefreshProgress()
}
