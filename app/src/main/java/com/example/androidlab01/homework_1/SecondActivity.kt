package com.example.androidlab01.homework_1

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
import com.example.androidlab01.R

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val DEFAULT_TEXT = stringResource(R.string.default_second_screen_text)
            val isTextFromMainExist = intent.getStringExtra("textFromMain") != null
            val secondScreenText = intent.getStringExtra("textFromMain") ?: DEFAULT_TEXT
            val context = LocalContext.current

            SecondScreen(secondScreenText, context, isTextFromMainExist)
        }
    }
}

@Composable
fun SecondScreen(
    secondScreenText: String,
    context: Context,
    isTextfromMainExist: Boolean
) {
    Column (
        modifier = Modifier.fillMaxHeight(0.8f).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = secondScreenText,
            fontSize = 25.sp,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = {
            navigateToMainActivity(context)
        },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(R.string.button_to_main), fontSize = 25.sp)
        }
        Button(
            onClick = {
                navigateToOthersActivity(
                    context,
                    ThirdActivity::class.java,
                    if (isTextfromMainExist) secondScreenText else ""
                )
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(R.string.button_to_third), fontSize = 25.sp)
        }
    }
}