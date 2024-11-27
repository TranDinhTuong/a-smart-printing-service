package com.example.asmartprintingservice.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionDTO(
    val id : Int,
    val amount : Int,
    val totalAmount : Int,
    val paperType : String,
    val transactionCode : String,
    val created_at : String,

    val user_id : String ?= null,
)
