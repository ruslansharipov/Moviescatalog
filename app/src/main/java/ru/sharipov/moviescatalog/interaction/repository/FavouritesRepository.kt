package ru.sharipov.moviescatalog.interaction.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.sharipov.moviescatalog.interaction.favourites.Favourite
import ru.sharipov.moviescatalog.interaction.favourites.MoviesDao

class FavouritesRepository(
    private val moviesDao: MoviesDao
) {

    fun saveId(id: Int): Completable {
        return moviesDao.insert(Favourite(id))
    }

    fun removeId(id: Int): Completable {
        return moviesDao.delete(Favourite(id))
    }

    fun isFavourite(id: Int): Single<Boolean> {
        return moviesDao.getCount(id)
            .map { it != 0 }
    }
}