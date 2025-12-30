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

data class LoginState(
    val login: String = "",
    val password: String = "",
    val loginError: String? = null,
    val passwordError: String? = null,
    val generalError: String? = null,
    val isSuccess: Boolean = false
)

class LoginViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
    private val validators: Validators
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onLoginChange(login: String) {
        _state.value = _state.value.copy(login = login, loginError = null, generalError = null)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password, passwordError = null, generalError = null)
    }

    fun login() {
        val currentState = _state.value

        //валидация
        val loginValidation = validators.validateLogin(currentState.login)
        val passwordValidation = validators.validatePassword(currentState.password)

        val loginError = (loginValidation as? ValidationResult.Error)?.message
        val passwordError = (passwordValidation as? ValidationResult.Error)?.message

        if (loginError != null || passwordError != null) {
            _state.value = currentState.copy(
                loginError = loginError,
                passwordError = passwordError
            )
            return
        }

        viewModelScope.launch {
            val result = userRepository.login(currentState.login, currentState.password)
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