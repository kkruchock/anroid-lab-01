package com.example.androidlab01.homework_2.present.screens.notesList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidlab01.homework_2.domain.model.Note

@Composable
fun NoteItem(note: Note) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = note.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        )
        if (note.content.isNotBlank()) {
            Text(
                text = note.content,
                fontSize = 14.sp
            )
        }
    }
}