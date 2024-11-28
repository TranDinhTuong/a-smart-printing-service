package com.example.asmartprintingservice.data.repository

import android.util.Log
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.RequestDTO

import com.example.asmartprintingservice.domain.model.RequestData
import com.example.asmartprintingservice.domain.repository.RequestRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RequestRepositorylmpl(private val client: SupabaseClient): RequestRepository {
    override suspend fun getAllRequestData(): Flow<Resource<List<RequestDTO>>> = flow {
        try{
            emit(Resource.Loading())

            val result = client
                .from("Request")
                .select()
                .decodeList<RequestDTO>()
                // coi chừng ko đúng tên cột
            if (result.isNotEmpty()) {
                emit(Resource.Success(result))
            } else {
                emit(Resource.Error("No requests found"))
            }
        }catch (e : Exception){
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }


    override suspend fun deleteRequest(id: String){
        try {
            client.from("Request").delete {
                filter {
                    RequestData::id eq id
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }

    }

    override suspend fun saveRequest(request: RequestData) {
        try {
            client.from("Request").insert(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun editRequest(request: RequestData)
    {
        try{
            deleteRequest(request.id)
            saveRequest(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}