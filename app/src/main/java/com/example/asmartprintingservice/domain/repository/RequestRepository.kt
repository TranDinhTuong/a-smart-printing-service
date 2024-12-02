package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.HistoryData
import kotlinx.coroutines.flow.Flow

interface RequestRepository {
    suspend fun getAllHistoryData(userId : String) : Flow<Resource<List<HistoryDataDTO>>>
    suspend fun getAllHistoryData() : Flow<Resource<List<HistoryDataDTO>>>
    suspend fun saveHistory(history : HistoryData): Flow<Resource<String>>
//    suspend fun searchHistory()
    suspend fun deleteHistory(id : Int)
    suspend fun getPendingRequests() : Flow<Resource<List<HistoryDataDTO>>>
    suspend fun updateRequest(history : HistoryDataDTO)
}