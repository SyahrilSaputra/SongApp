package com.learningas.songapps.room

import androidx.room.*

@Dao
interface SongDao {
    @Insert
    suspend fun addSong(song: Song)

    @Query("SELECT * FROM song ORDER BY id DESC")
    suspend fun getSongs() : List<Song>

    @Query("SELECT * FROM song WHERE id=:song_id")
    suspend fun getSong(song_id: Int) : List<Song>

    @Update
    suspend fun updateSong(song: Song)

    @Delete
    suspend fun deleteSong(song: Song)
}