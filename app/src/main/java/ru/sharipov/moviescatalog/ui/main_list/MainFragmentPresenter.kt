package ru.sharipov.moviescatalog.ui.main_list

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiConsumer
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import ru.sharipov.moviescatalog.domain.MovieItem
import ru.sharipov.moviescatalog.interaction.repository.MoviesRepository
import ru.sharipov.moviescatalog.interaction.repository.FavouritesRepository
import ru.sharipov.moviescatalog.interaction.response.Movie

@InjectViewState
class MainFragmentPresenter(
    private val moviesRepository: MoviesRepository,
    private val favouritesRepository: FavouritesRepository
) : MvpPresenter<MainView>() {

    companion object {
        private const val IMAGE_PREFIX = "https://image.tmdb.org/t/p/w500"
    }

    private val compositeDisposable = CompositeDisposable()
    private val dateParser = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

    override fun onFirstViewAttach() {
        fetchMovieList()
    }

    fun onError(t: Throwable) {
        viewState.onError()
    }

    fun onFavouriteClick(id: Int, isChecked: Boolean) {
        compositeDisposable += when (isChecked) {
            true -> favouritesRepository.saveId(id)
            false -> favouritesRepository.removeId(id)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun onItemClick(item: MovieItem) {
        viewState.showToast(item.title)
    }

    fun onRefresh(query: String) {
        viewState.hideSwipeRefreshProgress()
        onTextChanged(query)
    }

    fun onTextChanged(query: String) {
        if (query.isNotEmpty()) {
            fetchFilteredMovies(query)
        } else {
            fetchMovieList()
        }
    }

    private val movieListSubscriber: BiConsumer<List<MovieItem>, Throwable> = BiConsumer { movies, error ->
        when {
            error != null -> onError(error)
            movies.isEmpty() -> viewState.onEmptyList()
            else -> viewState.onListLoaded(movies)
        }
    }

    private fun fetchMovieList() {
        viewState.onListLoading()
        val movies = moviesRepository.getMovies()
        compositeDisposable += mapAndSubscribe(movies)
    }

    private fun fetchFilteredMovies(query: String) {
        viewState.onSearchLoading()
        val searchResult = moviesRepository.getSearchResult(query)
        compositeDisposable += mapAndSubscribe(searchResult)
    }

    private fun mapAndSubscribe(single: Single<List<Movie>>): Disposable {
        return single.flattenAsObservable { it }
            .flatMapSingle { movie ->
                Singles.zip(Single.just(movie), favouritesRepository.isFavourite(movie.id))
            }.map { pair: Pair<Movie, Boolean> ->
                mapMovieToItem(pair.first, pair.second)
            }.toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(movieListSubscriber)
    }

    private fun mapMovieToItem(movie: Movie, isFavourite: Boolean): MovieItem {
        val releaseDate = getReleaseDate(movie.releaseDate)
        val imageUrl = "$IMAGE_PREFIX${movie.posterPath}"
        return MovieItem(
            movie.id,
            imageUrl,
            movie.title,
            movie.overview,
            releaseDate,
            isFavourite
        )
    }

    private fun getReleaseDate(responseDate: String): String {
        return try {
            val date = LocalDate.parse(responseDate, dateParser)
            date.format(dateFormatter)
        } catch (ex: DateTimeParseException) {
            ""
        }
    }
}
