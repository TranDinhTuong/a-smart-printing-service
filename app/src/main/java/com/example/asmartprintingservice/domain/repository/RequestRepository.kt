package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource

import com.example.asmartprintingservice.data.model.RequestDTO

import com.example.asmartprintingservice.domain.model.RequestData
import kotlinx.coroutines.flow.Flow

interface RequestRepository {
    suspend fun getAllRequestData() : Flow<Resource<List<RequestDTO>>>
    suspend fun saveRequest(request : RequestData)
    //    suspend fun searchHistory()
    suspend fun deleteRequest(id : String)
    suspend fun editRequest(request : RequestData)
}