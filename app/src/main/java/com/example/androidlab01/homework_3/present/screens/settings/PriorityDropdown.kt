package com.example.androidlab01.homework_3.present.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.androidlab01.R
import com.example.androidlab01.homework_3.domain.model.NotificationPriority

@Composable
fun PriorityDropdown(
    selectedPriority: NotificationPriority,
    onPrioritySelected: (NotificationPriority) -> Unit
) {

    var opened by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier,
    ) {
        TextButton(
            onClick = {
                opened = true
            },
        ) {
            Text(stringResource(R.string.priority_label, stringResource(selectedPriority.titleRes)))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = opened,
            onDismissRequest = {
                opened = false
            }
        ) {
            NotificationPriority.entries.forEach { priority ->
                DropdownMenuItem(
                    text = {
                        Text(stringResource(priority.titleRes))
                           },
                    onClick = {
                        onPrioritySelected(priority)
                        opened = false
                    }
                )
            }
        }
    }
}