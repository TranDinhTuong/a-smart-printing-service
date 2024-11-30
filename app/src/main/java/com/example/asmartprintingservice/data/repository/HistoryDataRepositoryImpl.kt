package com.example.asmartprintingservice.data.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.HistoryData
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

    override suspend fun getAllHistoryData(userId : String): Flow<Resource<List<HistoryDataDTO>>> = flow {
        try{
            emit(Resource.Loading())

            val result = client
                .from("HistoryData")
                .select(columns = Columns.raw("*, File(*)"))
                {
                    filter {
                        eq("userId", userId)
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

    override suspend fun deleteHistory(id: Int): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading())
                client.from("HistoryData").delete {
                    filter {
                        HistoryDataDTO::id eq id
                    }
                }
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO).catch {
            emit(Resource.Error(it.message.toString()))
        }
    }


}