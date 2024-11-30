package com.example.asmartprintingservice.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class File(
    val name : String,
    val type : String,
    val time : String,
    val numberPages: Int,
    val userId: String ? = null,
)