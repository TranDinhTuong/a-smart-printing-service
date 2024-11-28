package com.example.asmartprintingservice.domain.model

import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.data.model.RequestStatus
import kotlinx.serialization.Serializable

@Serializable
class RequestData (
    val id : String,
    var paperType : String,
    val isColor : Boolean,
    val isSingleSided : Boolean,
    val receiptDate : String,
    val file_id: Int ?= null,
    val File : FileDTO?= null,
    val user_id: String?= null,
    val state: RequestStatus
)