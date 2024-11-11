package com.example.asmartprintingservice.data.model

import com.example.asmartprintingservice.domain.model.File
import kotlinx.serialization.Serializable

@Serializable
data class HistoryDataDTO(
    val id : Int,
    val paperType : String,
    val isColor : Boolean,
    val isSingleSided : Boolean,
    val receiptDate : String,

    val file_id: Int ?= null,
    val File : FileDTO?= null
)
