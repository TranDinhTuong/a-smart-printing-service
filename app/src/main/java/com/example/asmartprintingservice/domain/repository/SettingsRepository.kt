package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.Settings
import com.example.asmartprintingservice.domain.model.AcceptedFileType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSettings(): Flow<Resource<Settings>>
    suspend fun getAcceptedFileTypes(settingsId: Int): Flow<Resource<List<AcceptedFileType>>>
    suspend fun updateSettings(settings: Settings): Flow<Resource<Unit>>
    suspend fun addAcceptedFileType(fileType: AcceptedFileType): Flow<Resource<Unit>>
    suspend fun removeAcceptedFileType(file_type: String): Flow<Resource<Unit>>
}
