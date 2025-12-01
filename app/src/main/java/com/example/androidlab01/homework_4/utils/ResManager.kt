package com.example.androidlab01.homework_4.utils

import android.content.Context
import androidx.annotation.StringRes

class ResManager(
    private val context: Context
) {
    fun getString(@StringRes stringRes: Int): String {
        return context.getString(stringRes)
    }

    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any): String {
        return context.getString(stringRes, *formatArgs)
    }
}