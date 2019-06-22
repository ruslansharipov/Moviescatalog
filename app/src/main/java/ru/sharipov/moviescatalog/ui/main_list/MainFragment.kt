package ru.sharipov.moviescatalog.ui.main_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.redmadrobot.lib.sd.base.State
import com.redmadrobot.lib.sd.base.StateDelegate
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.sharipov.moviescatalog.R
import ru.sharipov.moviescatalog.app.MoviesApp
import ru.sharipov.moviescatalog.domain.MovieItem
import ru.sharipov.moviescatalog.ui.main_list.adapter.MoviesAdapter
import ru.sharipov.moviescatalog.ui.main_list.adapter.SimpleDecoration
import ru.sharipov.moviescatalog.ui.main_list.state.MovieState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainFragment : MvpAppCompatFragment(), MainView {

    @Inject
    lateinit var daggerPresenter: Lazy<MainFragmentPresenter>

    @InjectPresenter
    lateinit var presenter: MainFragmentPresenter

    @ProvidePresenter
    fun providePresenter(): MainFragmentPresenter = daggerPresenter.get()

    private val uiCompositeDisposable = CompositeDisposable()
    private val moviesAdapter = MoviesAdapter()

    private lateinit var stateDelegate: StateDelegate<MovieState>

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
        stateDelegate = StateDelegate(
            State(MovieState.LOADING_LIST, listOf(list_pb)),
            State(MovieState.LOADING_SEARCH, listOf(search_pb)),
            State(MovieState.EMPTY_LIST, listOf(empty_state_tv)),
            State(MovieState.ERROR, listOf(error_state_tv, retry_fab)),
            State(MovieState.LIST_LOADED, listOf(movies_rv))
        )

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

        uiCompositeDisposable += search_et.textChangeEvents()
            .skipInitialValue()
            .skip(1)
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { it.text.toString() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(presenter::onTextChanged, presenter::onError)

        swipe_rl.setOnRefreshListener { presenter.onRefresh(search_et.text.toString()) }
        retry_fab.setOnClickListener { presenter.onRefresh(search_et.text.toString()) }
    }

    override fun hideSwipeRefreshProgress() {
        swipe_rl.isRefreshing = false
    }

    override fun onDestroy() {
        uiCompositeDisposable.clear()
        super.onDestroy()
    }

    override fun onListLoading() {
        stateDelegate.currentState = MovieState.LOADING_LIST
    }

    override fun onSearchLoading() {
        stateDelegate.currentState = MovieState.LOADING_SEARCH
    }

    override fun onEmptyList() {
        stateDelegate.currentState = MovieState.EMPTY_LIST
        empty_state_tv.text = getString(R.string.empty_state_pattern, search_et.text.toString())
    }

    override fun onError() {
        stateDelegate.currentState = MovieState.ERROR
    }

    override fun onListLoaded(movies: List<MovieItem>) {
        moviesAdapter.movies = movies
        stateDelegate.currentState = MovieState.LIST_LOADED
    }
}