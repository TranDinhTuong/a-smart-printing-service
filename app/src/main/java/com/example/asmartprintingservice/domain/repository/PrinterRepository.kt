package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.PrinterDTO
import com.example.asmartprintingservice.domain.model.Printer
import kotlinx.coroutines.flow.Flow

interface PrinterRepository {
    suspend fun getAllPrinter() : Flow<Resource<List<PrinterDTO>>>
    suspend fun insertPrinter(printer : Printer)
    suspend fun updatePrinter(printer: Printer)
    suspend fun deletePrinter(id: String)
    suspend fun turnOffPrinter(id: String)
    suspend fun turnOnPrinter(id: String)
}