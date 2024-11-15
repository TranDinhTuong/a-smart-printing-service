package com.example.asmartprintingservice.data.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.PrinterDTO
import com.example.asmartprintingservice.domain.model.Printer
import com.example.asmartprintingservice.domain.repository.PrinterRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PrinterRepositoryImpl(
    private val client : SupabaseClient
) : PrinterRepository {

    override suspend fun getAllPrinter(): Flow<Resource<List<PrinterDTO>>> = flow {
        emit(Resource.Loading())
        val printers = client.from("Printer").select().decodeList<PrinterDTO>()
        emit(Resource.Success(printers))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }



    override suspend fun insertPrinter(printer: Printer) {
        try {
            client.from("Printer").insert(printer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun updatePrinter(printer: Printer) {
        try{
            client.from("Printer").update(printer)
            {
                filter {
                    Printer::id eq printer.id
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deletePrinter(id: Int) {
        try {
            client.from("Printer").delete {
                filter {
                    Printer::id eq id
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

}