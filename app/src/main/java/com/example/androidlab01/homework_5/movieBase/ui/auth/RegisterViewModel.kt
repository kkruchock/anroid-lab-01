package com.example.androidlab01.homework_5.movieBase.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab01.homework_5.movieBase.data.repository.UserRepository
import com.example.androidlab01.homework_5.movieBase.data.session.SessionManager
import com.example.androidlab01.homework_5.movieBase.domain.validator.ValidationResult
import com.example.androidlab01.homework_5.movieBase.domain.validator.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterState(
    val login: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val loginError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val generalError: String? = null,
    val isSuccess: Boolean = false
)

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
    private val validators: Validators
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onLoginChange(login: String) {
        _state.value = _state.value.copy(login = login, loginError = null, generalError = null)
    }

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email, emailError = null, generalError = null)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password, passwordError = null, generalError = null)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.value = _state.value.copy(confirmPassword = confirmPassword, confirmPasswordError = null, generalError = null)
    }

    fun register() {
        val currentState = _state.value

        //валидация
        val loginValidation = validators.validateLogin(currentState.login)
        val emailValidation = validators.validateEmail(currentState.email)
        val passwordValidation = validators.validatePassword(currentState.password)

        val loginError = (loginValidation as? ValidationResult.Error)?.message
        val emailError = (emailValidation as? ValidationResult.Error)?.message
        val passwordError = (passwordValidation as? ValidationResult.Error)?.message
        val confirmPasswordError = if (currentState.password != currentState.confirmPassword) {
            validators.getPasswordsMismatchError()
        } else null

        if (loginError != null || emailError != null || passwordError != null || confirmPasswordError != null) {
            _state.value = currentState.copy(
                loginError = loginError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
            return
        }

        viewModelScope.launch {
            val result = userRepository.register(
                login = currentState.login,
                email = currentState.email,
                password = currentState.password
            )
            result.fold(
                onSuccess = { user ->
                    sessionManager.saveUserId(user.id)
                    _state.value = _state.value.copy(isSuccess = true)
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(generalError = error.message)
                }
            )
        }
    }
}