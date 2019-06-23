package ru.sharipov.moviescatalog.interaction.favourites

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favourite::class], version = 1)
abstract class FavouriteDatabase: RoomDatabase() {
    abstract val moviesDao: MoviesDao
}