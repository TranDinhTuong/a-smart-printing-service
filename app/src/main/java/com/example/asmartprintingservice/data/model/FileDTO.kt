package com.example.asmartprintingservice.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FileDTO(
    val id : Int,
    val name : String,
    val type : String,
    val time : String,
    val numberPages : Int,

    val userId : String ?= null,
)