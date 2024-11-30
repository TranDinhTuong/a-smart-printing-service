package com.example.asmartprintingservice.data.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.Settings
import com.example.asmartprintingservice.domain.model.AcceptedFileType
import com.example.asmartprintingservice.domain.repository.SettingsRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : SettingsRepository {

    override suspend fun getSettings(): Flow<Resource<Settings>> = flow {
        emit(Resource.Loading())
        println("SettingsRepositoryImpl getSettings")
        try {
            val response = client.from("Settings")
                .select()
                .decodeSingle<Settings>()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error occurred"))
    }

    override suspend fun getAcceptedFileTypes(settingsId: Int): Flow<Resource<List<AcceptedFileType>>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.from("AcceptedFileTypes").select {
                filter {
                    eq("settings_id", settingsId)
                }
            }.decodeList<AcceptedFileType>()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error occurred"))
    }

    override suspend fun updateSettings(settings: Settings): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            client.from("Settings").update(settings)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error occurred"))
    }

    override suspend fun addAcceptedFileType(fileType: AcceptedFileType): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            client.from("AcceptedFileTypes")
                .insert(fileType)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error occurred"))
    }

    override suspend fun removeAcceptedFileType(fileTypeId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            client.from("AcceptedFileTypes")
                .delete {
                    filter {
                        eq("id", fileTypeId)
                    }
                }
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error occurred"))
    }
}