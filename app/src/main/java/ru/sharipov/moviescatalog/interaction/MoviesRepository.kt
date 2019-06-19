package ru.sharipov.moviescatalog.interaction

import io.reactivex.Single
import ru.sharipov.moviescatalog.interaction.network.MoviesApi
import ru.sharipov.moviescatalog.interaction.response.Movie

class MoviesRepository(private val moviesApi: MoviesApi) {
    fun getMovies(): Single<List<Movie>> {
        return moviesApi.getMovies()
            .map { it.results }
    }
}