package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.data.model.TransactionDTO
import com.example.asmartprintingservice.domain.model.Transaction

interface TransactionRepository {

    suspend fun getAllTransactions() : List<TransactionDTO>


}