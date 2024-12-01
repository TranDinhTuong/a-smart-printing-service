package com.example.asmartprintingservice.data.model

import com.example.asmartprintingservice.domain.model.File
import com.example.asmartprintingservice.domain.model.UserProfile
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.uuid.Uuid

@Serializable
data class HistoryDataDTO(
    val id : Int,
    val paperType : String,
    val isColor : Boolean,
    val isSingleSided : Boolean,
    val receiptDate : String,
    val status : Boolean,

    val file_id: Int ?= null,
    val File : FileDTO,

    val printer_id : String ?= null,
    val Printer: PrinterDTO?= null,

    val userId : String ?= null,
    val User : UserProfile ?= null,

    val numberPages : Int,
    val numberPrints : Int
)