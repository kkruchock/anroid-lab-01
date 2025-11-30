package com.example.androidlab01.homework_3.data

import com.example.androidlab01.homework_3.present.viewModel.NotificationUiState

object NotificationStore {
    private val activeNotifications = mutableMapOf<Int, NotificationUiState>()

    fun add(id: Int, state: NotificationUiState) {
        activeNotifications[id] = state
    }

    fun exists(id: Int): Boolean = activeNotifications.containsKey(id)

    fun get(id: Int): NotificationUiState? = activeNotifications[id]

    fun clear() {
        activeNotifications.clear()
    }

    fun isEmpty(): Boolean = activeNotifications.isEmpty()

    fun remove(id: Int) {
        activeNotifications.remove(id)
    }
}