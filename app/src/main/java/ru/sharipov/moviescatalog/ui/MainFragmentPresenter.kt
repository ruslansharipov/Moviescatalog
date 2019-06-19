package ru.sharipov.moviescatalog.ui

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import ru.sharipov.moviescatalog.interaction.MoviesRepository
import ru.sharipov.moviescatalog.interaction.response.Movie

class MainFragmentPresenter(
    private val repository: MoviesRepository
) : MvpPresenter<MainView>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        compositeDisposable += repository.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movies, error ->
                when {
                    error != null -> onError(error)
                    else -> onSuccess(movies)
                }
            }
    }

    private fun onSuccess(movies: List<Movie>) {

    }

    private fun onError(t: Throwable) {

    }


}
