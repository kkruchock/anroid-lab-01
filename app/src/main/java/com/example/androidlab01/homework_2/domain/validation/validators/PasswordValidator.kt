package com.example.androidlab01.homework_2.domain.validation.validators

import com.example.androidlab01.homework_2.domain.validation.errors.PasswordErrorType

fun validatePassword(password: String): PasswordErrorType? {

    return when {
        password.isBlank() -> PasswordErrorType.PASSWORD_EMPTY
        password.trim().length < 8 -> PasswordErrorType.PASSWORD_TOO_SHORT
        else -> null
    }
}