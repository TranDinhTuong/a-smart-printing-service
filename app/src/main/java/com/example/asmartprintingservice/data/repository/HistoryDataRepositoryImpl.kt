package com.example.asmartprintingservice.data.repository

import android.util.Log
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.domain.model.Printer
import com.example.asmartprintingservice.domain.repository.FileRepository
import com.example.asmartprintingservice.domain.repository.HistoryDataRepository
import com.example.asmartprintingservice.util.parseJsonData
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Objects
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class HistoryDataRepositoryImpl(
    private val client: SupabaseClient
) : HistoryDataRepository {

    override suspend fun getAllHistoryData(): Flow<Resource<List<HistoryDataDTO>>> = flow {
        try{
            emit(Resource.Loading())

            val result = client
                .from("HistoryData")
                .select(columns = Columns.raw("*, File(*)"))

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


    override suspend fun saveHistory(history: HistoryData) = flow {
        try {
            emit(Resource.Loading())
            client.from("HistoryData").insert(history)
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
            client.from("HistoryData").update({
                set("status", false)
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
                .from("HistoryData")
                .select(columns = Columns.raw("*, File(*), Printer(*)"))
                {
                    filter {
                        eq("status",false)
                    }
                }

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
            client.from("HistoryData").update(history) {
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