package com.example.androidlab01.homework_3.present.viewModel

import com.example.androidlab01.homework_3.domain.model.NotificationPriority

data class NotificationUiState(

    val title: String = "",
    val titleError: Boolean = false,
    val text: String = "",
    val priority: NotificationPriority = NotificationPriority.LOW,
    val bigTextEnabled: Boolean = false,
    val openMainEnabled: Boolean = false,
    val replyEnabled: Boolean = false,
)