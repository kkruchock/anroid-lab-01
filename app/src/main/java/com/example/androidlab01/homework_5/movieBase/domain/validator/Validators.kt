package com.example.androidlab01.homework_5.movieBase.domain.validator

class Validators(private val stringProvider: StringProvider) {

    private val minPasswordLength = 6
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Error(stringProvider.getEmailEmptyError())
            !emailRegex.matches(email) -> ValidationResult.Error(stringProvider.getEmailInvalidError())
            else -> ValidationResult.Success
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult.Error(stringProvider.getPasswordEmptyError())
            password.length < minPasswordLength -> ValidationResult.Error(stringProvider.getPasswordShortError(minPasswordLength))
            else -> ValidationResult.Success
        }
    }

    fun validateLogin(login: String): ValidationResult {
        return when {
            login.isBlank() -> ValidationResult.Error(stringProvider.getLoginEmptyError())
            login.length < 3 -> ValidationResult.Error(stringProvider.getLoginShortError())
            else -> ValidationResult.Success
        }
    }

    fun validateMovieTitle(title: String): ValidationResult {
        return when {
            title.isBlank() -> ValidationResult.Error(stringProvider.getTitleEmptyError())
            else -> ValidationResult.Success
        }
    }

    fun validateMovieRating(rating: Int): ValidationResult {
        return when {
            rating < 1 || rating > 10 -> ValidationResult.Error(stringProvider.getRatingInvalidError())
            else -> ValidationResult.Success
        }
    }

    fun getPasswordsMismatchError(): String = stringProvider.getPasswordsMismatchError()
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}