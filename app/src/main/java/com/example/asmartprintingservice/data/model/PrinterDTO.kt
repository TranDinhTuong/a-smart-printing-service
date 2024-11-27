package com.example.asmartprintingservice.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PrinterDTO(
    val id : String,
    val name : String,
    val address : String,
    val machineType : String,
    val dungTichKhayNap: Int,
    val dungTichKhayChua: Int,
    val paperTypes: List<PaperType>? = null, ///// ["A1","A2"]
    val state : PrinterStatus,
    val numberRequest: Int,
    val isDeleted: Boolean,

) {

}
