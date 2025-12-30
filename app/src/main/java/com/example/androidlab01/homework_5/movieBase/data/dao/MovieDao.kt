package com.example.androidlab01.homework_5.movieBase.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.androidlab01.homework_5.movieBase.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(movie: MovieEntity): Long

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE userId = :userId")
    fun getMoviesByUser(userId: Long): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE userId = :userId ORDER BY title ASC")
    fun getMoviesByUserSortedByTitleAsc(userId: Long): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE userId = :userId ORDER BY title DESC")
    fun getMoviesByUserSortedByTitleDesc(userId: Long): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE userId = :userId ORDER BY year DESC")
    fun getMoviesByUserSortedByYearDesc(userId: Long): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE userId = :userId ORDER BY year ASC")
    fun getMoviesByUserSortedByYearAsc(userId: Long): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE userId = :userId ORDER BY rating DESC")
    fun getMoviesByUserSortedByRatingDesc(userId: Long): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE userId = :userId ORDER BY rating ASC")
    fun getMoviesByUserSortedByRatingAsc(userId: Long): Flow<List<MovieEntity>>
}