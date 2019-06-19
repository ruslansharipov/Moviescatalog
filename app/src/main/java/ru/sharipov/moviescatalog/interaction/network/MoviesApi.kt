package ru.sharipov.moviescatalog.interaction.network

import io.reactivex.Single
import retrofit2.http.GET
import ru.sharipov.moviescatalog.interaction.response.MovieResponse

interface MoviesApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("discover/movie")
    fun getMovies(): Single<MovieResponse>
}