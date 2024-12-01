package com.example.asmartprintingservice.presentation.studentHistory

import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.CountRequest

data class PrinterHistoryState (
    val isLoading : Boolean = false,
    val errorMsg : String? = null,
    val histories: List<HistoryDataDTO> = emptyList(),
    val message : String? = null,
    val historyData: HistoryDataDTO?= null,

    val searchList :  List<HistoryDataDTO> = emptyList(),
    val isSearch : Boolean = false,

    val filteredDateList : List<HistoryDataDTO> = emptyList(),

    val startDate : String = "2024-11-02",
    val endDate : String = "2024-12-02"
)