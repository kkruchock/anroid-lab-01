package com.example.androidlab01.homework_2.present.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.androidlab01.homework_2.domain.model.Note

class NotesViewModel : ViewModel() {
    val notes = mutableStateListOf<Note>()

    fun addNote(note: Note) {
        notes.add(note)
    }
}