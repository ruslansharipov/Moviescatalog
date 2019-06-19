package ru.sharipov.moviescatalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Lazy
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.sharipov.moviescatalog.R
import ru.sharipov.moviescatalog.app.MoviesApp
import ru.sharipov.moviescatalog.domain.MovieItem
import javax.inject.Inject

class MainFragment : MvpAppCompatFragment(), MainView {
    @Inject
    lateinit var daggerPresenter: Lazy<MainFragmentPresenter>

    @InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    @ProvidePresenter
    fun providePresenter(): MainFragmentPresenter = daggerPresenter.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as MoviesApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun showMovies(movies: List<MovieItem>) {

    }


}