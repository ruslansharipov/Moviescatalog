package ru.sharipov.moviescatalog.data.repository

import io.reactivex.Single
import ru.sharipov.moviescatalog.data.network.MoviesApi
import ru.sharipov.moviescatalog.data.network.response.Movie
import ru.sharipov.moviescatalog.domain.IMoviesRepository

class MoviesRepository(
    private val moviesApi: MoviesApi
) : IMoviesRepository {
    override fun getMovies(): Single<List<Movie>> {
        return moviesApi.getMovies()
            .map { it.results }
    }

    override fun getSearchResult(query: String): Single<List<Movie>>{
        return moviesApi.getSearchResult(query)
            .map { it.results }
    }
}