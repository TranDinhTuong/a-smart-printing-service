package com.example.asmartprintingservice.presentation.user

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.repository.AuthRepository
import com.example.asmartprintingservice.domain.repository.TransactionRepository
import com.example.asmartprintingservice.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> get() = _userState

    init {
        getUserInfo(authRepository.getCurrentUser()?.id ?: "")
    }
    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.onChangeFullName -> {
                _userState.update {
                    it.copy(user = it.user?.copy(full_name = event.fullName))
                }
            }
            is UserEvent.onChangePhoneNumber -> {
                _userState.update {
                    it.copy(user = it.user?.copy(phone_number = event.phoneNumber))
                }
            }
            is UserEvent.updateFullNameAndPhoneNumber -> {
                updateFullNameAndPhoneNumber()
            }
        }
    }

    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()
    private fun getUserInfo(userId: String) {
        viewModelScope.launch {
            authRepository.getUserProfile(userId)
                .onStart { _userState.value = _userState.value.copy(isLoading = true) }
                .catch { e ->
                    _userState.value = UserState(error = e.message ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _userState.value = _userState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            _userState.value = UserState().copy(
                                user = result.data
                            )
                        }
                        is Resource.Error -> {
                            _userState.value = UserState(error = result.msg)
                        }
                    }
                }
        }
    }

    private fun updateFullNameAndPhoneNumber() {
        viewModelScope.launch {
            authRepository.updateUserProfile(
                userProfile = userState.value.user ?: return@launch,
            ).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _userState.value = _userState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            //_authState.value = AuthState().copy(user = result.data)

                            _snackbarEventFlow.emit(
                                SnackbarEvent.ShowSnackbar(
                                    message = "Cập nhật thông tin thành công",
                                    duration = SnackbarDuration.Short
                                )
                            )
                        }
                        is Resource.Error -> {
                            _userState.value = UserState(error = result.msg)

                            _snackbarEventFlow.emit(
                                SnackbarEvent.ShowSnackbar(
                                    message = "Cập nhật thông tin thật bại ${result.msg}",
                                    duration = SnackbarDuration.Short
                                )
                            )
                        }
                    }
            }
        }
    }
}