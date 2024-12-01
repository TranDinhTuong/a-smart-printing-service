package com.example.asmartprintingservice.data.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.TransactionDTO
import com.example.asmartprintingservice.domain.model.Transaction
import com.example.asmartprintingservice.domain.repository.TransactionRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TransactionRepositoryImpl (
    private val client : SupabaseClient
) : TransactionRepository {
    override suspend fun getTransactionsForLastMonth(userId : String): Flow<Resource<List<TransactionDTO>>> = flow {
        emit(Resource.Loading())
        try {
            val result = client
                .from("Transaction")
                .select{
                    filter {
                        eq("userId", userId)
                    }
                } // Lấy tất cả các cột từ bảng Transaction
                .decodeList<TransactionDTO>()

            if (result.isNotEmpty()) {
                emit(Resource.Success(result))
            } else {
                emit(Resource.Error("No transactions found"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override suspend fun insertTransaction(transaction: Transaction) = flow {
        try {
            emit(Resource.Loading())
            client.from("Transaction").insert(transaction)
            emit(Resource.Success("Success"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(Resource.Error(it.message.toString()))
    }

    override suspend fun updateTransaction(transaction: TransactionDTO) {
        try {
            client.from("Transaction").update(transaction) {
                filter {
                    TransactionDTO::id eq transaction.id
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteTransaction(id: Int) = flow {
        try {
            emit(Resource.Loading())
            client.from("Transaction").delete {
                filter {
                    TransactionDTO::id eq id
                }
            }
            emit(Resource.Success("Success"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(Resource.Error(it.message.toString()))
    }


}