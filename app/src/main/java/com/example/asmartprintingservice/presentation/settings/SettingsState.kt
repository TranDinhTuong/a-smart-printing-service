package com.example.asmartprintingservice.presentation.settings

import com.example.asmartprintingservice.domain.model.Settings
import com.example.asmartprintingservice.domain.model.AcceptedFileType

data class SettingsState(
    val isLoading: Boolean = false,
    val settings: Settings? = null,
    val acceptedFileTypes: List<AcceptedFileType> = emptyList(),
    val error: String = ""
)
