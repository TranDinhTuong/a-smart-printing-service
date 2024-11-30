package com.example.asmartprintingservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HistoryData(
    var paperType : String ,
    val isColor : Boolean ,
    val isSingleSided : Boolean ,
    val receiptDate : String,
    val file_id: Int ?= null,
    val printer_id : String ?= null,
    val userId : String ?= null,
    val numberPages : Int ?= null,
    val numberPrints : Int ?= null,
    val status : Boolean,
)