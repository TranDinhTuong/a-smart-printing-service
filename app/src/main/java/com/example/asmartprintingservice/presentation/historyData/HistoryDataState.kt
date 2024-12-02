package com.example.asmartprintingservice.presentation.historyData

import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.CountRequest

data class HistoryDataState (
    val isLoading : Boolean = false,
    val errorMsg : String? = null,
    val histories: List<HistoryDataDTO> = emptyList(),
    val message : String? = null,
    val printerCount : List<CountRequest> = emptyList(),
    val historyData: HistoryDataDTO ?= null,

    val searchList :  List<HistoryDataDTO> = emptyList(),
    val isSearch : Boolean = false,
    //val list: PostgrestResult ?= null,

    val paperType : String = "A3",
    val isColor : Boolean = false,
    val isSingleSided : Boolean = true,
    val receiptDate : String = "",
    val file_id : Int = 6
)