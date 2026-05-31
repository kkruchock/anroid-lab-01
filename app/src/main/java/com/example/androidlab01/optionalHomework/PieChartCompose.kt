package com.example.androidlab01.optionalHomework

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PieChart(
    segments: List<Pair<Any, Int>>,
    colors: List<Int>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            PieChartView(context).apply {
                setData(segments as List<Pair<String, Int>>, colors)
            }
        },
        update = { view ->
            view.setData(segments as List<Pair<String, Int>>, colors)
        },
        modifier = modifier
    )
}