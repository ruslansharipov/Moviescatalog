package ru.sharipov.moviescatalog.domain

import io.reactivex.Completable
import io.reactivex.Single

interface IFavouritesRepository {
    fun saveId(id: Int): Completable
    fun removeId(id: Int): Completable
    fun isFavourite(id: Int): Single<Boolean>
}