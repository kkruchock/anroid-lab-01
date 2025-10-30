package com.example.androidlab01.homework_2.domain.validation.errors

import com.example.androidlab01.R

enum class EmailErrorType(val message: Int) {
    EMAIL_EMPTY(R.string.error_email_empty),
    EMAIL_INVALID(R.string.error_email_invalid)
}