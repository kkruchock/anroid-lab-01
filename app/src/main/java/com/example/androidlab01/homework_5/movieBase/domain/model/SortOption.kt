package com.example.androidlab01.homework_5.movieBase.domain.model

import android.content.Context
import com.example.androidlab01.R

enum class SortOption {
    TITLE_ASC,
    TITLE_DESC,
    YEAR_DESC,
    YEAR_ASC,
    RATING_DESC,
    RATING_ASC;

    fun getDisplayName(context: Context): String {
        return when (this) {
            TITLE_ASC -> context.getString(R.string.movie_sort_title_asc)
            TITLE_DESC -> context.getString(R.string.movie_sort_title_desc)
            YEAR_DESC -> context.getString(R.string.movie_sort_year_desc)
            YEAR_ASC -> context.getString(R.string.movie_sort_year_asc)
            RATING_DESC -> context.getString(R.string.movie_sort_rating_desc)
            RATING_ASC -> context.getString(R.string.movie_sort_rating_asc)
        }
    }
}