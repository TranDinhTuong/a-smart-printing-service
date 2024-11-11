package com.example.asmartprintingservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HistoryData(
    var paperType : String ,
    val isColor : Boolean ,
    val isSingleSided : Boolean ,
    val receiptDate : String ,
    val file_id: Int ?= null,
)