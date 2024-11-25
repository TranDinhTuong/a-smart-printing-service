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

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> get() = _state

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn -> {
                viewModelScope.launch {
                    authRepository.signIn(event.email, event.password)
                        .onStart { _state.value = _state.value.copy(isLoading = true) }
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(isLoading = true)
                                }
                                is Resource.Success -> {
                                    _state.value = AuthState(user = result.data)
                                }
                                is Resource.Error -> {
                                    _state.value = AuthState(error = result.msg)
                                }
                            }
                        }
                }
            }
            is AuthEvent.SignUp -> {
                viewModelScope.launch {
                    authRepository.signUp(event.email, event.password, event.fullName, event.phoneNumber, event.role)
                        .onStart { _state.value = _state.value.copy(isLoading = true) }
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(isLoading = true)
                                }
                                is Resource.Success -> {
                                    _state.value = AuthState(user = result.data)
                                }
                                is Resource.Error -> {
                                    _state.value = AuthState(error = result.msg)
                                }
                            }
                        }
                }
            }
            is AuthEvent.SignOut -> {
                viewModelScope.launch {
                    authRepository.signOut()
                        .onStart { _state.value = _state.value.copy(isLoading = true) }
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(isLoading = true)
                                }
                                is Resource.Success -> {
                                    _state.value = AuthState()
                                }
                                is Resource.Error -> {
                                    _state.value = AuthState(error = result.msg)
                                }
                            }
                        }
                }
            }
        }
    }
}
