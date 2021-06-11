package com.learningas.songapps.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val judul: String,
    val lirik: String
)