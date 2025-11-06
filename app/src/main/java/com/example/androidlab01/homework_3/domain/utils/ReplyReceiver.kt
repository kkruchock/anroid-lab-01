package com.example.androidlab01.homework_3.domain.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.androidlab01.homework_3.data.MessagesRepository
import com.example.androidlab01.homework_3.domain.model.Keys
import com.example.androidlab01.homework_3.domain.model.Message

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val replyText = RemoteInput.getResultsFromIntent(intent)?.getCharSequence(Keys.TEXT_REPLY_KEY)?.toString()

        if (!replyText.isNullOrBlank()) {

            MessagesRepository.addMessage(Message(replyText))

            val notificationId = intent.getIntExtra(Keys.INTENT_REPLY_KEY, -1)

            if (notificationId != -1) {
                NotificationManagerCompat.from(context).cancel(notificationId)
            }
        }
    }
}