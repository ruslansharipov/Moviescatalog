package ru.sharipov.moviescatalog.data.favourites_datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey(autoGenerate = false)
    val id: Int
)