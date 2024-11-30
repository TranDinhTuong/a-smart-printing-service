package com.example.asmartprintingservice.data.model

import com.example.asmartprintingservice.domain.model.File
import com.example.asmartprintingservice.domain.model.Printer
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
    val File : FileDTO?= null,

    val printer_id : String ?= null,
    val printer: PrinterDTO?= null,

    val userId : String ?= null,
    val numberPages: Int ?= null,
    val numberPrints: Int ? = null,


)