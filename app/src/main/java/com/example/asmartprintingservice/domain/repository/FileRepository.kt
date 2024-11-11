package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.domain.model.File
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    suspend fun getFiles() : Flow<Resource<List<FileDTO>>>
    suspend fun saveFile(file : File)
    suspend fun uploadFile(name : String, byteArray: ByteArray) : String
    suspend fun deleteFile(id : Int)
}