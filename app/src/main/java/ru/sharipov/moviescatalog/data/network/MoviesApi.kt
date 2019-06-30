package ru.sharipov.moviescatalog.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.sharipov.moviescatalog.data.network.response.MovieResponse

interface MoviesApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("discover/movie")
    fun getMovies(): Single<MovieResponse>

    @GET("search/movie")
    fun getSearchResult(@Query("query") query: String): Single<MovieResponse>
}