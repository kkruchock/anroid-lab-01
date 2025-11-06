package com.example.androidlab01.homework_3.domain.model

data class Message(
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)
