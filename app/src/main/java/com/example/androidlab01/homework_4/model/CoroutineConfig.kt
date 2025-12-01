package com.example.androidlab01.homework_4.model

data class CoroutineConfig(

    val coroutineCount: Int = 10,
    val dispatcher: DispatcherType = DispatcherType.Default,
    val isSequential: Boolean = true,
    val isParallel: Boolean = false,
    val isLazy: Boolean = false,
    val isRunning: Boolean = false
)