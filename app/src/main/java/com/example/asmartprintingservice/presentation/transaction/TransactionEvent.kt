package com.example.asmartprintingservice.presentation.transaction

import com.example.asmartprintingservice.data.model.TransactionDTO
import com.example.asmartprintingservice.domain.model.Transaction

sealed class TransactionEvent {
    object getAllTransactions : TransactionEvent()

    data class insertTransaction(val transaction: Transaction) : TransactionEvent()

    data class deleteTransaction(val id: Int) : TransactionEvent()
}