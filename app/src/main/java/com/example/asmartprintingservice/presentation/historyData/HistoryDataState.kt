package com.example.asmartprintingservice.presentation.historyData

import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.CountRequest
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.domain.repository.HistoryDataRepository
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

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