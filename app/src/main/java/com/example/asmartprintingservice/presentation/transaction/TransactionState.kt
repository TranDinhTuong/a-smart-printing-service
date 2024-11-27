package com.example.asmartprintingservice.presentation.transaction

import com.example.asmartprintingservice.data.model.TransactionDTO

data class TransactionState (
    val isLoading : Boolean = false,
    val transactions : List<TransactionDTO> = emptyList(),
    val msg : String? = null
)