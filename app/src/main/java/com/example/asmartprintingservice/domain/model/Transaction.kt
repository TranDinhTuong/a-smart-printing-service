package com.example.asmartprintingservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val amount : Int,
    val totalAmount : Int,
    val paperType : String,
    val transactionCode : String,
    val userId : String ?= null
)
