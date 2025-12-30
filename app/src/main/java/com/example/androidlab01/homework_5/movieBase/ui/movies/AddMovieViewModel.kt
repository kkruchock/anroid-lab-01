package com.example.androidlab01.homework_5.movieBase.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab01.homework_5.movieBase.data.entity.MovieEntity
import com.example.androidlab01.homework_5.movieBase.data.repository.MovieRepository
import com.example.androidlab01.homework_5.movieBase.data.session.SessionManager
import com.example.androidlab01.homework_5.movieBase.domain.validator.ValidationResult
import com.example.androidlab01.homework_5.movieBase.domain.validator.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddMovieState(
    val title: String = "",
    val rating: String = "5",
    val year: String = "",
    val genre: String = "",
    val comment: String = "",
    val titleError: String? = null,
    val ratingError: String? = null,
    val isSuccess: Boolean = false
)

class AddMovieViewModel(
    private val movieRepository: MovieRepository,
    private val sessionManager: SessionManager,
    private val validators: Validators
) : ViewModel() {

    private val _state = MutableStateFlow(AddMovieState())
    val state: StateFlow<AddMovieState> = _state.asStateFlow()

    fun onTitleChange(title: String) {
        _state.value = _state.value.copy(title = title, titleError = null)
    }

    fun onRatingChange(rating: String) {
        _state.value = _state.value.copy(rating = rating, ratingError = null)
    }

    fun onYearChange(year: String) {
        //разрешаем только цифры
        if (year.isEmpty() || year.all { it.isDigit() }) {
            _state.value = _state.value.copy(year = year)
        }
    }

    fun onGenreChange(genre: String) {
        _state.value = _state.value.copy(genre = genre)
    }

    fun onCommentChange(comment: String) {
        _state.value = _state.value.copy(comment = comment)
    }

    fun saveMovie() {
        val currentState = _state.value

        //валидация
        val titleValidation = validators.validateMovieTitle(currentState.title)
        val ratingInt = currentState.rating.toIntOrNull() ?: 0
        val ratingValidation = validators.validateMovieRating(ratingInt)

        val titleError = (titleValidation as? ValidationResult.Error)?.message
        val ratingError = (ratingValidation as? ValidationResult.Error)?.message

        if (titleError != null || ratingError != null) {
            _state.value = currentState.copy(
                titleError = titleError,
                ratingError = ratingError
            )
            return
        }

        val userId = sessionManager.getUserId() ?: return

        viewModelScope.launch {
            val movie = MovieEntity(
                title = currentState.title,
                rating = ratingInt,
                userId = userId,
                year = currentState.year.toIntOrNull(),
                genre = currentState.genre.ifBlank { null },
                comment = currentState.comment.ifBlank { null }
            )
            movieRepository.addMovie(movie)
            _state.value = _state.value.copy(isSuccess = true)
        }
    }
}