package com.example.androidlab01.homework_3.present.viewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.androidlab01.homework_3.domain.model.NotificationPriority
import com.example.androidlab01.homework_3.domain.utils.NotificationHandler
import com.example.androidlab01.homework_3.domain.utils.ResManager

class AppViewModel(application: Application) : AndroidViewModel(application) {

    var uiState by mutableStateOf(NotificationUiState())
        private set

    private val notificationHandler = NotificationHandler(
        application,
        ResManager(application)
    ).apply {
        initChannels()
    }

    fun updateTitle(newTitle: String) {
        uiState = uiState.copy(
            title = newTitle,
            titleError = newTitle.isBlank()
        )
    }

    fun setTittleError(isError: Boolean) {
        uiState = uiState.copy(titleError = isError)
    }
    fun updateText(newText: String) {
        uiState = uiState.copy(
            text = newText,
            bigTextEnabled = if (newText.isBlank()) false else uiState.bigTextEnabled
        )
    }

    fun updatePriority(newPriority: NotificationPriority) {
        uiState = uiState.copy(priority = newPriority)
    }

    fun switchOpenMain(enabled: Boolean) {
        uiState = uiState.copy(openMainEnabled = enabled)
    }

    fun switchOpenBigText(enabled: Boolean) {
        uiState = uiState.copy(bigTextEnabled = enabled)
    }

    fun switchReply(enabled: Boolean) {
        uiState = uiState.copy(replyEnabled = enabled)
    }

    fun createNotification(): Int {
        return notificationHandler.showNotification(uiState)
    }
}