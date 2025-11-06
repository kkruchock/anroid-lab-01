package com.example.androidlab01.homework_3.domain.model

import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import androidx.annotation.StringRes
import com.example.androidlab01.R


enum class NotificationPriority(
    val compatPriority: Int,
    val importance: Int,
    val channelId: String,
    val channelNameRes: Int,
    @StringRes val titleRes: Int
) {
    MIN(
        NotificationCompat.PRIORITY_MIN,
        NotificationManager.IMPORTANCE_MIN,
        "min_channel",
        R.string.channel_min,
        R.string.priority_low
    ),
    LOW(
        NotificationCompat.PRIORITY_LOW,
        NotificationManager.IMPORTANCE_LOW,
        "low_channel",
        R.string.channel_low,
        R.string.priority_medium
    ),
    DEFAULT(
        NotificationCompat.PRIORITY_DEFAULT,
        NotificationManager.IMPORTANCE_DEFAULT,
        "default_channel",
        R.string.channel_default,
        R.string.priority_high
    ),
    HIGH(
        NotificationCompat.PRIORITY_HIGH, //или PRIORITY_MAX
        NotificationManager.IMPORTANCE_HIGH,
        "high_channel",
        R.string.channel_high,
        R.string.priority_urgent
    )
}