package com.example.asmartprintingservice.domain.model

import com.example.asmartprintingservice.data.model.PaperType
import com.example.asmartprintingservice.data.model.PrinterStatus
import kotlinx.serialization.Serializable

@Serializable
data class Printer(
    val id : String,
    val name : String,
    val address : String,
    val machineType : String,
    val dungTichKhayNap: Int,
    val dungTichKhayChua: Int,
    val paperTypes: List<PaperType>? = null, ///// ["A1","A2"]
    val state : PrinterStatus
)