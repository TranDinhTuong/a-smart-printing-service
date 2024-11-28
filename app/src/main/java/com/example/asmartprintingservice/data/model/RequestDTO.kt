package com.example.asmartprintingservice.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestDTO(

    val id : Int,
    val paperType : String,
    val isColor : Boolean,
    val isSingleSided : Boolean,
    val receiptDate : String,

    val file_id: Int ?= null,
    val File : FileDTO?= null,
    val user_id: String?= null,
    val state: RequestStatus

)
