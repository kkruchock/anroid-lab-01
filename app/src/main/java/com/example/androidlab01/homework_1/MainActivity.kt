package com.example.androidlab01.homework_1

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidlab01.R


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val inputText = remember { mutableStateOf("") }
            val context = LocalContext.current

            MainScreen(inputText, context)
        }
    }
}

@Composable
fun MainScreen(
    inputText: MutableState<String>,
    context: Context,
) {
    Column (
        modifier = Modifier.fillMaxHeight(0.8f).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = inputText.value,
            onValueChange = {
                inputText.value = it
            },
            textStyle = TextStyle(fontSize = 25.sp),
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                navigateToOthersActivity(context, SecondActivity::class.java, inputText.value)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = stringResource(R.string.button_to_second), fontSize = 25.sp)
        }
        Button(
            onClick = {
                navigateToOthersActivity(context, ThirdActivity::class.java, inputText.value)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = stringResource(R.string.button_to_third), fontSize = 25.sp)
        }
    }
}