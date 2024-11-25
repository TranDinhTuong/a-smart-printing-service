package com.example.asmartprintingservice.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> get() = _state

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.GetUserProfile -> {
                viewModelScope.launch {
                    userRepository.getUserProfile(event.userId)
                        .onStart { _state.value = _state.value.copy(isLoading = true) }
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(isLoading = true)
                                }
                                is Resource.Success -> {
                                    _state.value = UserState(userProfile = result.data)
                                }
                                is Resource.Error -> {
                                    _state.value = UserState(error = result.msg)
                                }
                            }
                        }
                }
            }
            is UserEvent.UpdateUserProfile -> {
                viewModelScope.launch {
                    userRepository.updateUserProfile(event.userProfile)
                        .onStart { _state.value = _state.value.copy(isLoading = true) }
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(isLoading = true)
                                }
                                is Resource.Success -> {
                                    _state.value = UserState(userProfile = event.userProfile)
                                }
                                is Resource.Error -> {
                                    _state.value = UserState(error = result.msg)
                                }
                            }
                        }
                }
            }
        }
    }
}
