package com.example.asmartprintingservice.presentation.settings

import com.example.asmartprintingservice.domain.model.Settings
import com.example.asmartprintingservice.domain.model.AcceptedFileType

sealed class SettingsEvent {
    data object LoadSettings : SettingsEvent()
    data class UpdateSettings(val settings: Settings) : SettingsEvent()
    data class AddFileType(val fileType: AcceptedFileType) : SettingsEvent()
    data class RemoveFileType(val fileType: String) : SettingsEvent()
}
