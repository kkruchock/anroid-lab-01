package com.example.androidlab01

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val textFromMain = intent.getStringExtra("textFromMain") ?: stringResource(R.string.default_third_screen_text)
            val context = LocalContext.current

            ThirdScreen(textFromMain, context)
        }
    }
}

@Composable
fun ThirdScreen(
    textFromMain: String,
    context: Context
) {
    Column (
        modifier = Modifier.fillMaxHeight(0.8f).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = textFromMain,
            fontSize = 25.sp,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = {
            navigateToMainActivity(context)
        }) {
            Text(text = stringResource(R.string.button_to_main),
                fontSize = 25.sp)
        }
    }
}