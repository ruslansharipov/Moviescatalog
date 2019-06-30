package ru.sharipov.moviescatalog.domain

import io.reactivex.Single
import ru.sharipov.moviescatalog.data.network.response.Movie

interface IMoviesRepository {
    fun getMovies(): Single<List<Movie>>
    fun getSearchResult(query: String): Single<List<Movie>>
}