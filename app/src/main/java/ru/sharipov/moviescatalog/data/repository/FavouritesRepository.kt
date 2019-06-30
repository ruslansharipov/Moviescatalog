package ru.sharipov.moviescatalog.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.sharipov.moviescatalog.data.favourites_datasource.Favourite
import ru.sharipov.moviescatalog.data.favourites_datasource.MoviesDao
import ru.sharipov.moviescatalog.domain.IFavouritesRepository

class FavouritesRepository(
    private val moviesDao: MoviesDao
): IFavouritesRepository {

    override fun saveId(id: Int): Completable {
        return moviesDao.insert(Favourite(id))
    }

    override fun removeId(id: Int): Completable {
        return moviesDao.delete(Favourite(id))
    }

    override fun isFavourite(id: Int): Single<Boolean> {
        return moviesDao.getCount(id)
            .map { it != 0 }
    }
}