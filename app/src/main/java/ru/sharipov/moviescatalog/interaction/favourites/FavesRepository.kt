package ru.sharipov.moviescatalog.interaction.favourites

import android.content.SharedPreferences
import androidx.core.content.edit

class FavesRepository(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val FAVOURITES = "FAVOURITES"
    }

    fun saveId(id: Int) {
        val newSet = getSet()
        newSet.add(id)
        saveSet(newSet)
    }

    fun removeId(id: Int) {

    }

    private fun getSet(): MutableSet<Int> {
        val stringSet = sharedPreferences.getStringSet(FAVOURITES, mutableSetOf())!!
        return stringSet.map { it.toInt() }
            .toMutableSet()
    }

    private fun saveSet(ids: Set<Int>) {

    }
}