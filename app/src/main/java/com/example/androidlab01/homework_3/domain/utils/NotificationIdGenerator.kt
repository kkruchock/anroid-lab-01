package com.example.androidlab01.homework_3.domain.utils

object NotificationIdGenerator {
    private var nextId = 1

    fun generateId(): Int {
        return nextId++
    }
}