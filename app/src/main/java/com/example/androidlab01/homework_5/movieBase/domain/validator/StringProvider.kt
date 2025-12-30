package com.example.androidlab01.homework_5.movieBase.domain.validator

import android.content.Context
import com.example.androidlab01.R

class StringProvider(private val context: Context) {

    fun getEmailEmptyError(): String = context.getString(R.string.movie_error_email_empty)
    fun getEmailInvalidError(): String = context.getString(R.string.movie_error_email_invalid)
    fun getPasswordEmptyError(): String = context.getString(R.string.movie_error_password_empty)
    fun getPasswordShortError(minLength: Int): String = context.getString(R.string.movie_error_password_short, minLength)
    fun getLoginEmptyError(): String = context.getString(R.string.movie_error_login_empty)
    fun getLoginShortError(): String = context.getString(R.string.movie_error_login_short)
    fun getTitleEmptyError(): String = context.getString(R.string.movie_error_title_empty)
    fun getRatingInvalidError(): String = context.getString(R.string.movie_error_rating_invalid)
    fun getPasswordsMismatchError(): String = context.getString(R.string.movie_error_passwords_mismatch)

    fun getUserExistsLoginError(): String = context.getString(R.string.movie_error_user_exists_login)
    fun getUserExistsEmailError(): String = context.getString(R.string.movie_error_user_exists_email)
    fun getUserNotFoundError(): String = context.getString(R.string.movie_error_user_not_found)
    fun getWrongPasswordError(): String = context.getString(R.string.movie_error_wrong_password)
}