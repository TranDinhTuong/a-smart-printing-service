package com.example.asmartprintingservice.domain.model

import com.example.asmartprintingservice.data.model.PaperType
import com.example.asmartprintingservice.data.model.PrinterStatus

data class Printer(
    val id : Int,
    val name : String,
    val address : String,
    val machineType : String,
    val dungTichKhayNap: Int,
    val dungTichKhayChua: Int,
    val paperTypes: Array<PaperType>,
    val state : PrinterStatus
)