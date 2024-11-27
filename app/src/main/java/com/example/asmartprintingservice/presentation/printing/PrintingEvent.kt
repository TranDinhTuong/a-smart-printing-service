package com.example.asmartprintingservice.presentation.printing

import com.example.asmartprintingservice.data.model.PrinterDTO

sealed class PrintingEvent {
    data class onChangePrinter(val printer: PrinterDTO) : PrintingEvent()

    data class onChangePaperType(val paperType: String) : PrintingEvent()

    data class onChangeColor(val isColor: Boolean) : PrintingEvent()

    data class onChangeSingleSided(val isSingleSided: Boolean) : PrintingEvent()

    data class onChangeReceiptDate(val receiptDate: String) : PrintingEvent()
}