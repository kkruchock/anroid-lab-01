package com.example.androidlab01.homework_5.movieBase.data.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun saveUserId(userId: Long) {
        prefs.edit().putLong(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): Long? {
        val userId = prefs.getLong(KEY_USER_ID, -1L)
        return if (userId == -1L) null else userId
    }

    fun isLoggedIn(): Boolean {
        return getUserId() != null
    }

    fun logout() {
        prefs.edit().remove(KEY_USER_ID).apply()
    }

    companion object {
        private const val PREFS_NAME = "movie_base_session"
        private const val KEY_USER_ID = "user_id"
    }
}