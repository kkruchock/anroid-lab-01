package com.example.androidlab01.homework_3.present.screens.edit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import com.example.androidlab01.R
import com.example.androidlab01.homework_3.data.NotificationStore
import com.example.androidlab01.homework_3.domain.utils.NotificationHandler
import com.example.androidlab01.homework_3.domain.utils.ResManager

@Composable
fun EditScreen(
    ctx: Context,
    resManager: ResManager,
    handler: NotificationHandler

) {
    var idText by remember { mutableStateOf("") }
    var newText by remember { mutableStateOf("") }
    var idError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ){
        OutlinedTextField(
            value = idText,
            onValueChange = {
                idText = it
                idError = false
            },
            label = {
                Text(stringResource(R.string.edit_notification_id_label))
            },
            isError = idError,
            supportingText = {
                if (idError) {
                    Text(stringResource(R.string.empty_id_error))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newText,
            onValueChange = { newText = it },
            label = {
                Text(stringResource(R.string.edit_notification_text_label))
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (idText.isBlank()) {
                    idError = true
                    return@Button
                }

                val id = idText.toIntOrNull()
                if (id != null && NotificationStore.exists(id)) {

                    val isActiveInSystem = NotificationManagerCompat.from(ctx)
                        .activeNotifications
                        .any { it.id == id }

                    if (isActiveInSystem) {
                        val updatedState = NotificationStore.get(id)!!.copy(text = newText)
                        handler.showNotification(updatedState, id)
                        Toast.makeText(ctx, resManager.getString(R.string.notification_updated_toast), Toast.LENGTH_SHORT).show()
                    } else {
                        NotificationStore.remove(id)
                        Toast.makeText(ctx, resManager.getString(R.string.no_notification_toast), Toast.LENGTH_SHORT).show()
                    }


                } else {
                    Toast.makeText(ctx, resManager.getString(R.string.no_notification_toast), Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.update_notification_button))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (NotificationStore.isEmpty()) {
                    Toast.makeText(ctx, resManager.getString(R.string.no_notifications_toast), Toast.LENGTH_SHORT).show()
                } else {
                    NotificationManagerCompat.from(ctx).cancelAll()
                    NotificationStore.clear()
                    Toast.makeText(ctx, resManager.getString(R.string.notifications_deleted_toast), Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(stringResource(R.string.clear_notifications_button))
        }
    }
}