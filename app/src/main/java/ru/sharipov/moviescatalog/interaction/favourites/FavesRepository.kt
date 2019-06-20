package ru.sharipov.moviescatalog.interaction.favourites

import android.content.SharedPreferences

class FavesRepository(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val FAVOURITES = "FAVOURITES"
    }

    private val favourites: MutableSet<Int> = sharedPreferences.getStringSet(FAVOURITES, mutableSetOf())!!
        .map { it.toInt() }
        .toMutableSet()

    fun saveId(id: Int) {
        favourites.add(id)
        saveSet(favourites)
    }

    fun removeId(id: Int) {
        favourites.remove(id)
        saveSet(favourites)
    }

    private fun saveSet(ids: Set<Int>) {
        val set = ids.map { it.toString() }.toSet()
        sharedPreferences.edit()
            .putStringSet(FAVOURITES, set)
            .apply()
    }

    fun isFavourite(id: Int): Boolean {
        return favourites.contains(id)
    }
}