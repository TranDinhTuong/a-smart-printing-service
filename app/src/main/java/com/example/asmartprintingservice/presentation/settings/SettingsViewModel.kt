package com.example.asmartprintingservice.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.Settings
import com.example.asmartprintingservice.domain.model.AcceptedFileType
import com.example.asmartprintingservice.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState>
        get() = _settingsState

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.LoadSettings -> {
                Log.d("LoadSettings", "load()")
                loadSettings()
            }
            is SettingsEvent.UpdateSettings -> {
                Log.d("UpdateSettings", "update()")
                updateSettings(event.settings)
            }
            is SettingsEvent.AddFileType -> {
                addAcceptedFileType(event.fileType)
            }
            is SettingsEvent.RemoveFileType -> {
                Log.d("RemoveFileType", "remove()")
                removeAcceptedFileType(event.fileType)
            }

        }
    }

    private fun loadSettings() {
        Log.d("LoadSettings", "stage2")
        viewModelScope.launch {
            combine(
                settingsRepository.getSettings(),
                settingsRepository.getAcceptedFileTypes(1) // just default settings id
            ) { settingsResult, fileTypesResult ->
                Pair(settingsResult, fileTypesResult)
            }
                .onStart { _settingsState.value = _settingsState.value.copy(isLoading = true) }
                .catch { e ->
                    _settingsState.value = SettingsState(error = e.message ?: "Unknown error")
                }
                .collect { (settingsResult, fileTypesResult) ->
                    when {
                        settingsResult is Resource.Success && fileTypesResult is Resource.Success -> {
                            _settingsState.value = SettingsState(
                                settings = settingsResult.data,
                                acceptedFileTypes = fileTypesResult.data!!

                            )
                            Log.d("LoadSettings", "success")
                        }
                        settingsResult is Resource.Error -> {
                            _settingsState.value = SettingsState(error = settingsResult.msg ?: "Failed to load settings")
                        }
                        fileTypesResult is Resource.Error -> {
                            _settingsState.value = SettingsState(error = fileTypesResult.msg ?: "Failed to load file types")
                        }
                    }
                }
        }
    }

    private fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            settingsRepository.updateSettings(settings)
                .onStart { _settingsState.value = _settingsState.value.copy(isLoading = true) }
                .catch { e ->
                    _settingsState.value = SettingsState(error = e.message ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            loadSettings()
                        }
                        is Resource.Error -> {
                            _settingsState.value = SettingsState(error = result.msg ?: "Failed to update settings")
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun addAcceptedFileType(fileType: AcceptedFileType) {
        viewModelScope.launch {
            settingsRepository.addAcceptedFileType(fileType)
                .onStart { _settingsState.value = _settingsState.value.copy(isLoading = true) }
                .catch { e ->
                    _settingsState.value = SettingsState(error = e.message ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            loadSettings()
                        }
                        is Resource.Error -> {
                            _settingsState.value = SettingsState(error = result.msg ?: "Failed to add file type")
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun removeAcceptedFileType(fileTypeId: String) {
        Log.d("RemoveFileType", "removeAcceptedFileType called()")
        viewModelScope.launch {
            settingsRepository.removeAcceptedFileType(fileTypeId)
                .onStart { _settingsState.value = _settingsState.value.copy(isLoading = true) }
                .catch { e ->
                    _settingsState.value = SettingsState(error = e.message ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            loadSettings() // Reload settings to reflect removal
                        }
                        is Resource.Error -> {
                            _settingsState.value = SettingsState(error = result.msg ?: "Failed to remove file type")
                        }
                        else -> {}
                    }
                }
        }
    }


}
