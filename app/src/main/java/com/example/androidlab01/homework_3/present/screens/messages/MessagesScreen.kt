package com.example.androidlab01.homework_3.present.screens.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidlab01.R
import com.example.androidlab01.homework_3.data.MessagesRepository
import com.example.androidlab01.homework_3.domain.model.Message

@Composable
fun MessagesScreen() {

    var text by remember { mutableStateOf("") }

    val messages = MessagesRepository.messages

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = {
                Text(stringResource(R.string.enter_message_label))
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (text.isNotBlank()) {
                    MessagesRepository.addMessage(Message(text))
                    text = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.send_button))
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages.sortedByDescending {
                it.timestamp
            })
            { msg ->
                Text(msg.text)
            }
        }
    }
}