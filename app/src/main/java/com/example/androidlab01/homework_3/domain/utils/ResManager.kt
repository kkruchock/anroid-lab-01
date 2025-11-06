package com.example.androidlab01.homework_3.domain.utils

import android.content.Context
import androidx.annotation.StringRes

class ResManager(
    private val ctx: Context
) {

    fun getString(@StringRes stringRes: Int): String {
        return ctx.getString(stringRes)
    }
}