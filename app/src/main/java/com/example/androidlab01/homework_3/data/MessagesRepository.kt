package com.example.androidlab01.homework_3.data

import androidx.compose.runtime.mutableStateListOf
import com.example.androidlab01.homework_3.domain.model.Message

object MessagesRepository {

    val messages = mutableStateListOf<Message>()

    fun addMessage(msg: Message) {
        messages.add(msg)
    }
}