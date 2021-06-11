package com.learningas.songapps.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Song::class],
    version = 1
)
abstract class SongDB : RoomDatabase() {
    abstract fun songDao() : SongDao

    companion object {

        @Volatile private var instance : SongDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            SongDB::class.java,
            "song12345.db"
        ).build()

    }
}