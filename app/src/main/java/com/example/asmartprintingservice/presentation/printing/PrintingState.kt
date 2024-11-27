package com.example.asmartprintingservice.presentation.printing

import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.data.model.PaperType
import com.example.asmartprintingservice.data.model.PrinterDTO

data class PrintingState(
    val isLoading : Boolean = false,
    val errorMsg : String? = null,

    val printers: List<PrinterDTO> = emptyList(), // Loại máy in
    val selectedPrinter: PrinterDTO? = null,

    val paperType: String = "A4", // Cỡ giấy
    val receiveDate: String = "27 Nov 2024", // Ngày nhận
    val printQuantity: Int = 0, // Số lượng bản in
    val paperNeeded: String = "100", // Số giấy cần để in
    val isOneSided: Boolean = true, // In 1 mặt
    val isColored: Boolean = false // In màu
)