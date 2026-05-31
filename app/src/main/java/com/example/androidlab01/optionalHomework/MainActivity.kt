package com.example.androidlab01.optionalHomework

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PieChart(
                segments = listOf(
                    "xlsx"   to 46,
                    "dll"    to 18,
                    "xml"    to 17,
                    "zip"    to 5,
                    "xlsm"   to 7,
                    "config" to 5,
                    "pdb"    to 2,
                ),
                colors = listOf(
                    Color.parseColor("#4472C4"),
                    Color.parseColor("#ED7D31"),
                    Color.parseColor("#A5A5A5"),
                    Color.parseColor("#FFC000"),
                    Color.parseColor("#5B9BD5"),
                    Color.parseColor("#70AD47"),
                    Color.parseColor("#264478"),
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}