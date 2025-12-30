package com.example.androidlab01.homework_5.movieBase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidlab01.homework_5.movieBase.data.dao.MovieDao
import com.example.androidlab01.homework_5.movieBase.data.dao.UserDao
import com.example.androidlab01.homework_5.movieBase.data.entity.MovieEntity
import com.example.androidlab01.homework_5.movieBase.data.entity.UserEntity

@Database(
    entities = [UserEntity::class, MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}