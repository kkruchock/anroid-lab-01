package com.example.androidlab01.homework_2.domain.validation.validators

import com.example.androidlab01.homework_2.domain.validation.errors.EmailErrorType

private val EMAIL_REGEX = """^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$""".toRegex()
fun validateEmail(email: String): EmailErrorType? {

    return when {
        email.isBlank() -> EmailErrorType.EMAIL_EMPTY
        !EMAIL_REGEX.matches(email) -> EmailErrorType.EMAIL_INVALID
        else -> null
    }
}