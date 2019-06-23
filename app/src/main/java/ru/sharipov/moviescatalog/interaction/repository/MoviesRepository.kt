package ru.sharipov.moviescatalog.interaction.repository

import io.reactivex.Single
import ru.sharipov.moviescatalog.interaction.network.MoviesApi
import ru.sharipov.moviescatalog.interaction.network.response.Movie

class MoviesRepository(private val moviesApi: MoviesApi) {
    fun getMovies(): Single<List<Movie>> {
        return moviesApi.getMovies()
            .map { it.results }
    }

    fun getSearchResult(query: String): Single<List<Movie>>{
        return moviesApi.getSearchResult(query)
            .map { it.results }
    }
}