package ru.sharipov.moviescatalog.ui.main_list

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiConsumer
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import ru.sharipov.moviescatalog.domain.MovieItem
import ru.sharipov.moviescatalog.interaction.MoviesRepository
import ru.sharipov.moviescatalog.interaction.favourites.FavesRepository
import ru.sharipov.moviescatalog.interaction.response.Movie

@InjectViewState
class MainFragmentPresenter(
    private val moviesRepository: MoviesRepository,
    private val favesRepository: FavesRepository
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

    private fun fetchMovieList() {
        viewState.showListProgress()
        compositeDisposable += moviesRepository.getMovies()
            .flattenAsObservable { it }
            .map { movie: Movie -> mapMovieToItem(movie) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(movieListSubscriber)
    }

    private fun mapMovieToItem(movie: Movie): MovieItem {
        val date = LocalDate.parse(movie.releaseDate, dateParser)
        val releaseDate = date.format(dateFormatter)
        val imageUrl = "$IMAGE_PREFIX${movie.posterPath}"
        val isFavourite = favesRepository.isFavourite(movie.id)
        return MovieItem(
            movie.id,
            imageUrl,
            movie.title,
            movie.overview,
            releaseDate,
            isFavourite
        )
    }

    private fun onSuccess(movies: List<MovieItem>) {
        viewState.hideListProgress()
        viewState.hideSearchProgress()
        viewState.showMovies(movies)
    }

    private fun onError(t: Throwable) {
        viewState.hideListProgress()
        viewState.hideSearchProgress()
    }

    fun onFavouriteClick(id: Int, isChecked: Boolean) = when (isChecked) {
        true -> favesRepository.saveId(id)
        false -> favesRepository.removeId(id)
    }

    fun onRefresh(searchInput: String) {
        viewState.hideSwipeRefresh()
        if (searchInput.isNotEmpty()) {
            onTextChanged(searchInput)
        } else {
            fetchMovieList()
        }
    }

    fun onTextChanged(query: String) {
        viewState.showSearchProgress()
        compositeDisposable += moviesRepository.getSearchResult(query)
            .flattenAsObservable { it }
            .map { movie: Movie -> mapMovieToItem(movie) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(movieListSubscriber)
    }

    private val movieListSubscriber: BiConsumer<List<MovieItem>, Throwable> = BiConsumer { movies, error ->
        when {
            error != null -> onError(error)
            else -> onSuccess(movies)
        }
    }
}
