package com.example.androidlab01.homework_2.domain.validation.errors

import com.example.androidlab01.R

enum class PasswordErrorType(val message: Int) {
    PASSWORD_EMPTY(R.string.error_password_empty),
    PASSWORD_TOO_SHORT(R.string.error_password_too_short)
}