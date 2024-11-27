package com.example.asmartprintingservice.data.repository

import android.util.Log
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.PrinterDTO
import com.example.asmartprintingservice.domain.model.Printer
import com.example.asmartprintingservice.domain.repository.PrinterRepository
import com.example.asmartprintingservice.util.parseJsonData
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
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
        try {
            // Truy vấn cơ sở dữ liệu để lấy danh sách máy in
            val result = client
                .from("Printer")
                .select(){
                    filter { PrinterDTO::isDeleted eq false }
                }
                .decodeList<PrinterDTO>()

            // Log dữ liệu nhận được từ truy vấn
            Log.d("PrinterRepository", "Fetched printers: $result")

            // Kiểm tra xem kết quả có rỗng không
            if (result.isNotEmpty()) {
                emit(Resource.Success(result))
            } else {
                emit(Resource.Error("No printers found"))
            }

        } catch (e: Exception) {
            // Log ngoại lệ nếu có lỗi xảy ra
            Log.e("PrinterRepository", "Error fetching printers: ${e.message}")
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }




    override suspend fun insertPrinter(printer: Printer) {
        try {
            Log.d("INSERT_PRINTER", "insertPrinter")
            client.from("Printer").insert(printer)
        } catch (e: Exception) {
            Log.d("INSERT_PRINTER_ERROR", "chả bt sao sai :vv")
            e.printStackTrace()
        }
    }

    override suspend fun updatePrinter(printer: Printer) {
        try{
            client.from("Printer").update(printer) {
                filter {
                    Printer::id eq printer.id
                }
            }
            Log.d("UPDATEOK", "PRINTER: $printer")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deletePrinter(id: String) {
        try {
            client.from("Printer").update({
                PrinterDTO::isDeleted setTo "True"
            }) {
                filter {
                    Printer::id eq id
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }




    override suspend fun turnOffPrinter(id: String) {
        try {
            client.from("Printer").update(mapOf("status" to "OFF")) {
                filter {
                    Printer::id eq id
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun turnOnPrinter(id: String) {
        try {
            client.from("Printer").update(mapOf("status" to "ON")) {
                filter {
                    Printer::id eq id
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}