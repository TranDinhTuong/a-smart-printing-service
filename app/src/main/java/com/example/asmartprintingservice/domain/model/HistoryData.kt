package com.example.asmartprintingservice.domain.model

import com.example.asmartprintingservice.data.model.FileDTO
import kotlinx.serialization.Serializable

@Serializable
data class HistoryData(
    var paperType : String ,
    val isColor : Boolean ,
    val isSingleSided : Boolean ,
    val receiptDate : String ,
    val file_id: Int ?= null,
    val status: Boolean,
    val printer_id : String ?= null,
    val userId : String ?= null,
)