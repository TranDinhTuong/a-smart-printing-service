package com.example.asmartprintingservice.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> get() = _authState

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn -> {
                signIn(event.email, event.password)
            }
            is AuthEvent.SignUp -> {
                signUp(event.email, event.password, event.fullName, event.phoneNumber, event.role)
            }
            is AuthEvent.SignOut -> {
                signOut()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(email, password)
                .onStart { _authState.value = _authState.value.copy(isLoading = true) }
                .catch { e ->
                    _authState.value = AuthState(error = e.message ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _authState.value = _authState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            _authState.value = AuthState(user = result.data)
                        }
                        is Resource.Error -> {
                            _authState.value = AuthState(error = result.msg)
                        }
                    }
                }
        }
    }

    private fun signUp(email: String, password: String, fullName: String, phoneNumber: String, role: String) {
        viewModelScope.launch {
            authRepository.signUp(email, password, fullName, phoneNumber, role)
                .onStart { _authState.value = _authState.value.copy(isLoading = true) }
                .catch { e ->
                    _authState.value = AuthState(error = e.message ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _authState.value = _authState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            _authState.value = AuthState(user = result.data)
                        }
                        is Resource.Error -> {
                            _authState.value = AuthState(error = result.msg)
                        }
                    }
                }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
                .onStart { _authState.value = _authState.value.copy(isLoading = true) }
                .catch { e ->
                    _authState.value = AuthState(error = e.message ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _authState.value = _authState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            _authState.value = AuthState()
                        }
                        is Resource.Error -> {
                            _authState.value = AuthState(error = result.msg)
                        }
                    }
                }
        }
    }
}
