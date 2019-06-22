package ru.sharipov.moviescatalog.ui.main_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.textChangeEvents
import dagger.Lazy
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins.onError
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.sharipov.moviescatalog.R
import ru.sharipov.moviescatalog.app.MoviesApp
import ru.sharipov.moviescatalog.domain.MovieItem
import ru.sharipov.moviescatalog.ui.hide
import ru.sharipov.moviescatalog.ui.main_list.adapter.MoviesAdapter
import ru.sharipov.moviescatalog.ui.main_list.adapter.SimpleDecoration
import ru.sharipov.moviescatalog.ui.show
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainFragment : MvpAppCompatFragment(), MainView {

    @Inject
    lateinit var daggerPresenter: Lazy<MainFragmentPresenter>

    @InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    @ProvidePresenter
    fun providePresenter(): MainFragmentPresenter = daggerPresenter.get()

    private val moviesAdapter = MoviesAdapter()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(view) {
        val inner = resources.getDimensionPixelSize(R.dimen.fragment_main_padding_inner)
        val small = resources.getDimensionPixelSize(R.dimen.fragment_main_padding_small)
        val big = resources.getDimensionPixelSize(R.dimen.fragment_main_padding_big)
        val decoration = SimpleDecoration(small, inner, big)
        moviesAdapter.favouritesListener = presenter::onFavouriteClick
        movies_rv.run {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(decoration)
        }

        search_et.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onTextChanged(s.toString())
            }

        })

        swipe_rl.setOnRefreshListener { presenter.onRefresh(search_et.text.toString()) }
    }

    override fun showMovies(movies: List<MovieItem>) {
        moviesAdapter.movies = movies
    }

    override fun showListProgress() = list_pb.show()

    override fun hideListProgress() = list_pb.hide()

    override fun showSearchProgress() = search_pb.show()

    override fun hideSearchProgress() = search_pb.hide()

    override fun hideSwipeRefresh() {
        swipe_rl.isRefreshing = false
    }
}