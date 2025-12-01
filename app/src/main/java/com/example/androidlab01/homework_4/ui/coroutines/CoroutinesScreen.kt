package com.example.androidlab01.homework_4.ui.coroutines

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidlab01.R
import com.example.androidlab01.homework_4.model.DispatcherType
import com.example.androidlab01.homework_4.utils.CoroutineConstans
import com.example.androidlab01.homework_4.utils.ResManager

@Composable
fun CoroutinesScreen() {
    val context = LocalContext.current
    val resManager = remember { ResManager(context) }
    val viewModel = remember {
        CoroutinesViewModel(resManager)
    }
    val config = viewModel.config.value
    val snackbarMessage = viewModel.snackbarMessage
    val toastMessage = viewModel.toastMessage
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(stringResource(R.string.coroutine_count, config.coroutineCount))
            Slider(
                value = config.coroutineCount.toFloat(),
                onValueChange = {
                    viewModel.updateCoroutineCount(it.toInt())
                },
                valueRange = CoroutineConstans.SLIDER_MIN.toFloat()..CoroutineConstans.SLIDER_MAX.toFloat(),
                steps = (CoroutineConstans.SLIDER_MAX - CoroutineConstans.SLIDER_MIN) / CoroutineConstans.SLIDER_STEP - 1
            )

            DispatcherDropdown(
                selectedDispatcher = config.dispatcher,
                onDispatcherSelected = viewModel::updateDispatcher
            )

            SwitchRow(
                text = stringResource(R.string.sequential_switch),
                checked = config.isSequential,
                onCheckedChange = viewModel::updateSequential
            )

            SwitchRow(
                text = stringResource(R.string.parallel_switch),
                checked = config.isParallel,
                onCheckedChange = viewModel::updateParallel
            )

            SwitchRow(
                text = stringResource(R.string.lazy_switch),
                checked = config.isLazy,
                onCheckedChange = viewModel::updateLazy
            )

            if (config.isRunning) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator()
                    Button(
                        onClick = {
                            viewModel.cancelCoroutines()
                        }
                    ) {
                        Text(stringResource(R.string.cancel_button))
                    }
                }
            } else {
                Button(onClick = viewModel::startCoroutines) {
                    Text(stringResource(R.string.start_button))
                }
            }
        }
    }
}

@Composable
private fun DispatcherDropdown(
    selectedDispatcher: DispatcherType,
    onDispatcherSelected: (DispatcherType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = {
            expanded = true
        }) {
            Text(stringResource(R.string.dispatcher_button, selectedDispatcher.name))
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            DispatcherType.entries.forEach { dispatcher ->
                DropdownMenuItem(
                    text = {
                        Text(dispatcher.name)
                    },
                    onClick = {
                        onDispatcherSelected(dispatcher)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun SwitchRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}