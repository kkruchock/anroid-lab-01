package com.example.androidlab01.homework_3.present.screens.settings

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import com.example.androidlab01.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidlab01.homework_3.present.utils.NotificationPermissionHelper
import com.example.androidlab01.homework_3.present.viewModel.AppViewModel

@Composable
fun SettingsScreen(appViewModel: AppViewModel) {

    val state = appViewModel.uiState
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = state.title,
            onValueChange = {
                appViewModel.updateTitle(it)

            },
            label = {
                Text(stringResource(R.string.message_label_title))
            },
            isError = state.titleError,
            supportingText = {
                if (state.titleError) {
                    Text(stringResource(R.string.message_error_title))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = state.text,
            onValueChange = {
                appViewModel.updateText(it)

            },
            label = {
                Text(stringResource(R.string.message_label_text))
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.message_open_main_switch))

            Switch(
                checked = state.openMainEnabled,
                onCheckedChange = {
                    appViewModel.switchOpenMain(it)
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.message_reply_switch))

            Switch(
                checked = state.replyEnabled,
                onCheckedChange = {
                    appViewModel.switchReply(it)
                },
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.message_open_big_text_switch))

            Switch(
                checked = state.bigTextEnabled,
                onCheckedChange = {
                    appViewModel.switchOpenBigText(it)
                },
                enabled = state.text.isNotBlank()
            )
        }

        Spacer(Modifier.height(8.dp))

        PriorityDropdown(
            selectedPriority = state.priority,
            onPrioritySelected = { appViewModel.updatePriority(it) }
        )

        Button(
            onClick = {
                if (state.title.isBlank()) {
                    appViewModel.setTittleError(true)
                    return@Button
                }
                if (NotificationPermissionHelper.hasNotificationPermission(context)) {
                    val id = appViewModel.createNotification()
                    Toast.makeText(
                        context,
                        context.getString(R.string.notification_created_toast, id),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    NotificationPermissionHelper.requestNotificationPermission(context as Activity)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.message_create_notification))
        }
    }
}