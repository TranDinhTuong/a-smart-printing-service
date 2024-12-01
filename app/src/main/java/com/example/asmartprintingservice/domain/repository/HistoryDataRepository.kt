package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.File
import com.example.asmartprintingservice.domain.model.HistoryData
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.flow.Flow
import java.util.Objects
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

interface HistoryDataRepository {
    suspend fun getAllHistoryData(userId : String) : Flow<Resource<List<HistoryDataDTO>>>
    suspend fun saveHistory(history : HistoryData): Flow<Resource<String>>
//    suspend fun searchHistory()
    suspend fun deleteHistory(id : Int)
    suspend fun getPendingRequests() : Flow<Resource<List<HistoryDataDTO>>>
    suspend fun updateRequest(history : HistoryDataDTO)
}