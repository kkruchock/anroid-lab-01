package com.example.androidlab01.homework_2.present.screens.noteAdding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.androidlab01.R
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidlab01.homework_2.domain.model.Note

@Composable
fun NoteAddingScreen(
    onNoteSavedClick: (Note) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }
    var titleError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier,
            value = title,
            onValueChange = {
                title = it
                titleError = false
            },
            label = {
                Text(stringResource(R.string.label_title))
            },
            isError = titleError,
            supportingText = {
                if (titleError) {
                    Text(stringResource(R.string.error_title_empty))
                }
            }
        )

        OutlinedTextField(
            modifier = Modifier,
            value = content,
            label = {
                Text(stringResource(R.string.label_content))
            },
            onValueChange = {
                content = it
            },
        )

        Button(
            modifier = Modifier,
            onClick = {
                if (title.isNotBlank()) {
                    onNoteSavedClick(Note(title, content))
                } else {
                    titleError = true
                }
            }
        ) {
            Text(stringResource(R.string.save_note_button))
        }
    }
}