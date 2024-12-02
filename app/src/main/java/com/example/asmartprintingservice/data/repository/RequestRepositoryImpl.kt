package com.example.asmartprintingservice.data.repository

import android.util.Log
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.domain.repository.RequestRepository
import com.example.asmartprintingservice.util.parseJsonData
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RequestRepositoryImpl(
    private val client: SupabaseClient
) : RequestRepository {

    override suspend fun getAllHistoryData(userId : String): Flow<Resource<List<HistoryDataDTO>>> = flow {
        try{
            emit(Resource.Loading())

            val result = client
                .from("Request")
                .select(columns = Columns.raw("*, File(*)"))
                {
                    filter {
                        eq("userId", userId)
                    }
                }

            Log.d("getAllHistoryData", "Raw result: ${result.data}")

            if (!parseJsonData(result.data).isNullOrEmpty()) {
                emit(Resource.Success(parseJsonData(result.data)))
            } else {
                emit(Resource.Error("No data found"))
            }
        }catch (e : Exception){
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(Resource.Error(it.message.toString()))
    }

    override suspend fun getAllHistoryData(): Flow<Resource<List<HistoryDataDTO>>> = flow {
        try{
            emit(Resource.Loading())

            val result = client
                .from("Request")
                .select(columns = Columns.raw("*, File(*), User(*), Printer(*)"))

            if (!parseJsonData(result.data).isNullOrEmpty()) {
                emit(Resource.Success(parseJsonData(result.data)))
            } else {
                emit(Resource.Error("No data found"))
            }
        }catch (e : Exception){
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(Resource.Error(it.message.toString()))
    }

    override suspend fun saveHistory(history: HistoryData) = flow {
        try {
            emit(Resource.Loading())
            client.from("Request").insert(history)
            emit(Resource.Success("Success"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(Resource.Error(it.message.toString()))
    }

    override suspend fun deleteHistory(id: Int) {

        try {
            Log.d("deletehistory", "turn to true")
            client.from("Request").update({
                set("status", true)
            }) {
                filter {
                    eq("id",id)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override suspend fun getPendingRequests(): Flow<Resource<List<HistoryDataDTO>>> = flow {
        try{
            emit(Resource.Loading())

            val result = client
                .from("Request")
                .select(columns = Columns.raw("*, File(*)"))
                {
                    filter {
                        eq("status",false)
                    }
                }
                Log.d("HistoryData", "Result: $result")

            if (!parseJsonData(result.data).isNullOrEmpty()) {
                emit(Resource.Success(parseJsonData(result.data)))
            } else {
                emit(Resource.Error("No data found"))
            }
        }catch (e : Exception){
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(Resource.Error(it.message.toString()))
    }

    override suspend fun updateRequest(history: HistoryDataDTO) {
        try{
            client.from("Request").update(history) {
                filter {
                    eq("id",history.id)
                }
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }


}