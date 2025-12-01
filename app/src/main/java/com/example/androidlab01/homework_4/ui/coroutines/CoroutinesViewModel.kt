package com.example.androidlab01.homework_4.ui.coroutines

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab01.R
import com.example.androidlab01.homework_4.model.CoroutineConfig
import com.example.androidlab01.homework_4.model.DispatcherType
import com.example.androidlab01.homework_4.model.exceptions.CancellationCoroutineException
import com.example.androidlab01.homework_4.model.exceptions.IllegalStateCoroutineException
import com.example.androidlab01.homework_4.model.exceptions.TimeoutCoroutineException
import com.example.androidlab01.homework_4.utils.CoroutineConstans
import com.example.androidlab01.homework_4.utils.ResManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class CoroutinesViewModel(
    private val resManager: ResManager
) : ViewModel() {

    val config = mutableStateOf(CoroutineConfig())

    private var activeJobs: List<Job> = emptyList()

    private val _snackbarMessage = mutableStateOf<String?>(null)
    val snackbarMessage: String? get() = _snackbarMessage.value

    private val _toastMessage = mutableStateOf<String?>(null)
    val toastMessage: String? get() = _toastMessage.value

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun updateCoroutineCount(count: Int) {
        config.value = config.value.copy(coroutineCount = count)
    }

    fun updateDispatcher(dispatcher: DispatcherType) {
        config.value = config.value.copy(dispatcher = dispatcher)
    }

    fun updateSequential(isSequential: Boolean) {
        config.value = config.value.copy(
            isSequential = isSequential,
            isParallel = !isSequential
        )
    }

    fun updateParallel(isParallel: Boolean) {
        config.value = config.value.copy(
            isParallel = isParallel,
            isSequential = !isParallel
        )
    }

    fun updateLazy(isLazy: Boolean) {
        config.value = config.value.copy(isLazy = isLazy)
    }

    fun startCoroutines() {
        config.value = config.value.copy(isRunning = true)
        activeJobs = emptyList()

        viewModelScope.launch {
            launchCoroutines()
            config.value = config.value.copy(isRunning = false)
        }
    }

    fun cancelCoroutines() {
        config.value = config.value.copy(isRunning = false)

        val activeCount = activeJobs.count { it.isActive }

        activeJobs.forEach { job ->
            job.cancel()
        }

        activeJobs = emptyList()

        _toastMessage.value = resManager.getString(R.string.canceled_message, activeCount)
    }

    private suspend fun launchCoroutines() {
        val dispatcher = getDispatcher()

        if (config.value.isSequential) {
            for (i in 0 until config.value.coroutineCount) {
                val job = launchSingleCoroutine(i, dispatcher)
                activeJobs = listOf(job)
                job.join()
            }
        } else {
            val jobs = mutableListOf<Job>()
            repeat(config.value.coroutineCount) { index ->
                val job = launchSingleCoroutine(index, dispatcher)
                jobs.add(job)

                if (config.value.isLazy) {
                    job.start()
                }
            }
            activeJobs = jobs
            jobs.joinAll()
        }
    }

    private fun getDispatcher(): CoroutineDispatcher {
        return when (config.value.dispatcher) {
            DispatcherType.Default -> Dispatchers.Default
            DispatcherType.IO -> Dispatchers.IO
            DispatcherType.Main -> Dispatchers.Main
        }
    }

    private fun launchSingleCoroutine(index: Int, dispatcher: CoroutineDispatcher): Job {
        val startMode = if (config.value.isLazy) CoroutineStart.LAZY
        else CoroutineStart.DEFAULT

        return viewModelScope.launch(dispatcher, startMode) {
            runCatching {
                emulateHeavyOperation(index)
            }.onFailure { exception ->
                handleCoroutineException(exception, index)
            }
        }
    }

    private suspend fun emulateHeavyOperation(index: Int) {
        val delayTime = (CoroutineConstans.MIN_DELAY_MS..CoroutineConstans.MAX_DELAY_MS).random()
        delay(delayTime)

        if (delayTime >= CoroutineConstans.EXCEPTION_THRESHOLD_MS &&
            (1..100).random() <= CoroutineConstans.EXCEPTION_CHANCE_PERCENT) {
            throwRandomException()
        }
    }

    private fun throwRandomException() {
        when ((1..CoroutineConstans.EXCEPTION_TYPES_COUNT).random()) {
            1 -> throw TimeoutCoroutineException()
            2 -> throw CancellationCoroutineException()
            3 -> throw IllegalStateCoroutineException()
        }
    }

    private fun handleCoroutineException(exception: Throwable, index: Int) {
        when (exception) {
            is TimeoutCoroutineException -> {
                _snackbarMessage.value = resManager.getString(R.string.timeout_message, index)
            }
            is CancellationCoroutineException -> {
                resetSettings()
            }
            is CancellationException -> {

            }
            is IllegalStateCoroutineException -> {
                _toastMessage.value = resManager.getString(R.string.illegal_state_message, index)
            }
        }
    }

    private fun resetSettings() {
        config.value = config.value.copy(
            coroutineCount = 10,
            dispatcher = DispatcherType.Default,
            isSequential = true,
            isParallel = false,
            isLazy = false
        )
    }
}