package com.example.androidlab01.homework_3.domain.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.androidlab01.R
import com.example.androidlab01.homework_3.data.NotificationStore
import com.example.androidlab01.homework_3.domain.model.Keys
import com.example.androidlab01.homework_3.domain.model.NotificationPriority
import com.example.androidlab01.homework_3.present.activity.MainActivity
import com.example.androidlab01.homework_3.present.viewModel.NotificationUiState

class NotificationHandler(
    private val ctx: Context,
    private val resManager: ResManager,
) {

    private val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun initChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationPriority.entries.forEach { priority ->
                val channel = NotificationChannel(
                    priority.channelId,
                    resManager.getString(priority.channelNameRes),
                    priority.importance
                )
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    @SuppressLint("MissingPermission", "NotificationPermission")
    fun showNotification(uiState: NotificationUiState, id: Int? = null) : Int {

        val id = id ?: NotificationIdGenerator.generateId()

        val builder = NotificationCompat.Builder(ctx, uiState.priority.channelId)
            .setSmallIcon(R.drawable.ic_notification_24)
            .setContentTitle(uiState.title)
            .setContentText(uiState.text)
            .setPriority(uiState.priority.compatPriority)

        if (uiState.bigTextEnabled) {
            builder.setStyle(NotificationCompat.BigTextStyle().bigText(uiState.text))
        }

        if (uiState.openMainEnabled) {
            val intent = Intent(ctx, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(Keys.INTENT_TITLE_KEY, uiState.title)
                putExtra(Keys.INTENT_TEXT_KEY, uiState.text)
            }

            val pendingIntent = PendingIntent.getActivity(
                ctx,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            builder.setContentIntent(pendingIntent)
        }

        if (uiState.replyEnabled) {
            val remoteInput = RemoteInput.Builder(Keys.TEXT_REPLY_KEY)
                .setLabel(resManager.getString(R.string.reply_label))
                .build()

            val replyIntent = Intent(ctx, ReplyReceiver::class.java).apply {
                putExtra(Keys.INTENT_REPLY_KEY, id)
            }

            val replyPendingIntent = PendingIntent.getBroadcast(
                ctx,
                id,
                replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

            val replyAction = NotificationCompat.Action.Builder(
                R.drawable.ic_reply_24,
                resManager.getString(R.string.reply_action),
                replyPendingIntent
            ).addRemoteInput(remoteInput).build()

            builder.addAction(replyAction)

        }

        NotificationManagerCompat.from(ctx).notify(id, builder.build())
        NotificationStore.add(id, uiState)

        return id
    }
}