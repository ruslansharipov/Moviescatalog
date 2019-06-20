package ru.sharipov.moviescatalog.domain

data class MovieItem(
    val id: Int,
    val image: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val isFavourite: Boolean
)
