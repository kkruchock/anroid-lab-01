package com.example.androidlab01.homework_5.movieBase.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab01.homework_5.movieBase.data.repository.UserRepository
import com.example.androidlab01.homework_5.movieBase.data.session.SessionManager
import com.example.androidlab01.homework_5.movieBase.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileState(
    val user: User? = null,
    val isLoggedOut: Boolean = false
)

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val userId = sessionManager.getUserId() ?: return@launch
            val userEntity = userRepository.getUserById(userId)
            userEntity?.let {
                _state.value = _state.value.copy(
                    user = User(
                        id = it.id,
                        login = it.login,
                        email = it.email
                    )
                )
            }
        }
    }

    fun logout() {
        sessionManager.logout()
        _state.value = _state.value.copy(isLoggedOut = true)
    }
}