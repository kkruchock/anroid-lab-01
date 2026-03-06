package com.example.androidlab01.homework_5.movieBase.domain.model

data class Movie(
    val id: Long = 0,
    val title: String,
    val rating: Int,
    val userId: Long,
    val year: Int? = null,
    val genre: String? = null,
    val comment: String? = null
)