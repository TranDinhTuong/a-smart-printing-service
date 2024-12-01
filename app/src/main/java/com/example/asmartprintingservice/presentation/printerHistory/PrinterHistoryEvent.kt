package com.example.asmartprintingservice.presentation.PrinterHistory

import com.example.asmartprintingservice.presentation.printing.PrintingEvent

sealed class PrinterHistoryEvent{
    data object getAllPrinterHistory : PrinterHistoryEvent()

    data class onSerarchPrinterHistory(val searchQuery: String) : PrinterHistoryEvent()

    data class onFilterDatePrinterHistory(val startDate: String, val endDate: String) : PrinterHistoryEvent()

    data class onChangeStartDate(val date: String) : PrinterHistoryEvent()

    data class onChangeEndDate(val date: String) : PrinterHistoryEvent()
}