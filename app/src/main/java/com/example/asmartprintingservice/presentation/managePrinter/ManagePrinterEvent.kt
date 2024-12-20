package com.example.asmartprintingservice.presentation.managePrinter

import com.example.asmartprintingservice.data.model.PrinterDTO
import com.example.asmartprintingservice.domain.model.Printer


sealed class ManagePrinterEvent {
    data object LoadPrinters : ManagePrinterEvent()
    data class DeletePrinters(val id : String) : ManagePrinterEvent()
    data class SearchPrinters(val query: String) : ManagePrinterEvent()
    data class UpdatePrinterStatus(val printer: PrinterDTO, val check: Boolean) : ManagePrinterEvent()
    data class InsertPrinter(val printer: Printer): ManagePrinterEvent()

    data class getPrinterById(val id: String): ManagePrinterEvent()
}