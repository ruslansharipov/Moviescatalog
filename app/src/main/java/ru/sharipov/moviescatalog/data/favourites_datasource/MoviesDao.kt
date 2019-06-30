package ru.sharipov.moviescatalog.data.favourites_datasource

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Query("SELECT count(id) FROM favourite WHERE id = :id LIMIT 1")
    fun getCount(id: Int): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favourite: Favourite): Completable

    @Delete
    fun delete(favourite: Favourite): Completable
}