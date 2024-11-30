package com.example.asmartprintingservice.data.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.domain.model.File
import com.example.asmartprintingservice.domain.repository.FileRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FileRepositoryImpl (
    private val client : SupabaseClient
): FileRepository {

    override suspend fun getFiles() : Flow<Resource<List<FileDTO>>> = flow {
        emit(Resource.Loading())
        val result = client.from("File").select().decodeList<FileDTO>().sortedByDescending { it -> it.id }
        emit(Resource.Success(result))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override suspend fun saveFile(file : File) = flow {
        emit(Resource.Loading())
        client.from("File").insert(file)
        emit(Resource.Success("Success"))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override suspend fun uploadFile(name : String , byteArray: ByteArray) : String{
        try {
            val bucket = client.storage.from("files")
            return bucket.upload(name, byteArray) { upsert = true }.key ?: ""
        }catch (e: Exception){
            e.printStackTrace()
        }
        return ""
    }

    override suspend fun deleteFile(id : Int) {
        client.from("File").delete{
            filter {
                FileDTO::id eq id
            }
        }
    }
}