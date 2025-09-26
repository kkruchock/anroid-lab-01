package com.example.androidlab01

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

fun navigateToMainActivity(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT) //не понял, как сделать, чтобы создался новый экземпляр первого экрана и при этом не удалилсь другие, пока только перемещаю
    context.startActivity(intent)
}

fun navigateToOthersActivity(context: Context, activityClass: Class<*>, text: String) {
    val intent = Intent(context, activityClass)
    if (text.isNotBlank()) {
        intent.putExtra("textFromMain", text.trim())
    }
    context.startActivity(intent)
}