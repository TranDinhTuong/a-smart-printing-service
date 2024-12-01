package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.TransactionDTO
import com.example.asmartprintingservice.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactionsForLastMonth(userId : String): Flow<Resource<List<TransactionDTO>>>
    suspend fun insertTransaction(transaction: Transaction) : Flow<Resource<String>>
    suspend fun updateTransaction(transaction: TransactionDTO)
    suspend fun deleteTransaction(id: Int) : Flow<Resource<String>>
}