package com.example.androidlab01.homework_5.movieBase.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab01.homework_5.movieBase.data.entity.MovieEntity
import com.example.androidlab01.homework_5.movieBase.data.repository.MovieRepository
import com.example.androidlab01.homework_5.movieBase.data.session.SessionManager
import com.example.androidlab01.homework_5.movieBase.domain.model.SortOption
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MoviesState(
    val sortOption: SortOption = SortOption.TITLE_ASC,
    val showSortBottomSheet: Boolean = false
)

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModel(
    private val movieRepository: MovieRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state.asStateFlow()

    private val userId: Long = sessionManager.getUserId() ?: 0L

    val movies: StateFlow<List<MovieEntity>> = _state
        .flatMapLatest { state ->
            movieRepository.getMovies(userId, state.sortOption)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun showSortBottomSheet() {
        _state.value = _state.value.copy(showSortBottomSheet = true)
    }

    fun hideSortBottomSheet() {
        _state.value = _state.value.copy(showSortBottomSheet = false)
    }

    fun setSortOption(sortOption: SortOption) {
        _state.value = _state.value.copy(sortOption = sortOption, showSortBottomSheet = false)
    }

    fun deleteMovie(movie: MovieEntity) {
        viewModelScope.launch {
            movieRepository.deleteMovie(movie)
        }
    }
}