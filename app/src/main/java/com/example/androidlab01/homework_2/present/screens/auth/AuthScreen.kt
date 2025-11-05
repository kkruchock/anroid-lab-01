package com.example.androidlab01.homework_2.present.screens.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.androidlab01.R
import com.example.androidlab01.homework_2.domain.validation.errors.EmailErrorType
import com.example.androidlab01.homework_2.domain.validation.errors.PasswordErrorType
import com.example.androidlab01.homework_2.domain.validation.validators.validateEmail
import com.example.androidlab01.homework_2.domain.validation.validators.validatePassword


@Composable
fun AuthScreen(
    onSuccessAuth: (String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var emailError by rememberSaveable { mutableStateOf<EmailErrorType?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<PasswordErrorType?>(null) }

    fun validateInputs(): Boolean {
        emailError = validateEmail(email)
        passwordError = validatePassword(password)

        return emailError == null && passwordError == null
    }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                modifier = Modifier,
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                label = {
                    Text(stringResource(R.string.label_email))
                },
                isError = emailError != null,
                supportingText = {
                    emailError?.let {
                        Text(stringResource(it.message))
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier,
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                label = {
                    Text(stringResource(R.string.label_password))
                },
                isError = passwordError != null,
                supportingText = {
                    passwordError?.let {
                        Text(stringResource(it.message))
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Text(
                            if (passwordVisible) stringResource(R.string.hide_button)
                            else stringResource(R.string.show_button)
                        )
                    }
                }
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .imePadding(),
            onClick = {
                if (validateInputs()) {
                    onSuccessAuth(email)
                }
            }
        ) {
            Text(stringResource(R.string.sign_in_button))
        }
    }
}