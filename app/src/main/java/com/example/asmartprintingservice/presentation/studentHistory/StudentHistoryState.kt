package com.example.asmartprintingservice.presentation.studentHistory

import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.CountRequest

data class StudentHistoryState (
    val isLoading : Boolean = false,
    val errorMsg : String? = null,
    val histories: List<HistoryWithStudentName> = emptyList(),
    val message : String? = null,
    val historyData: HistoryDataDTO?= null,

    val searchList :  List<HistoryWithStudentName> = emptyList(),
    val isSearch : Boolean = false,
)

data class HistoryWithStudentName(
    val id: Int,
    val studentName: String,
    val status: Boolean,
    val receiptDate: String,
    val paperType: String,
    val numberPages: Int,
)