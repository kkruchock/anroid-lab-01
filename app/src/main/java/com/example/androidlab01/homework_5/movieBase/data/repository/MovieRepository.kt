package com.example.androidlab01.homework_5.movieBase.data.repository

import com.example.androidlab01.homework_5.movieBase.data.dao.MovieDao
import com.example.androidlab01.homework_5.movieBase.data.entity.MovieEntity
import com.example.androidlab01.homework_5.movieBase.domain.model.SortOption
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    fun getMovies(userId: Long, sortOption: SortOption): Flow<List<MovieEntity>> {
        return when (sortOption) {
            SortOption.TITLE_ASC -> movieDao.getMoviesByUserSortedByTitleAsc(userId)
            SortOption.TITLE_DESC -> movieDao.getMoviesByUserSortedByTitleDesc(userId)
            SortOption.YEAR_DESC -> movieDao.getMoviesByUserSortedByYearDesc(userId)
            SortOption.YEAR_ASC -> movieDao.getMoviesByUserSortedByYearAsc(userId)
            SortOption.RATING_DESC -> movieDao.getMoviesByUserSortedByRatingDesc(userId)
            SortOption.RATING_ASC -> movieDao.getMoviesByUserSortedByRatingAsc(userId)
        }
    }

    suspend fun addMovie(movie: MovieEntity): Long {
        return movieDao.insert(movie)
    }

    suspend fun deleteMovie(movie: MovieEntity) {
        movieDao.delete(movie)
    }
}