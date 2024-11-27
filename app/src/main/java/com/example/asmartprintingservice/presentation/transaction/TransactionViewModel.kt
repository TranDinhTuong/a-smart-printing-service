package com.example.asmartprintingservice.presentation.transaction

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.TransactionDTO
import com.example.asmartprintingservice.domain.model.Transaction
import com.example.asmartprintingservice.domain.repository.TransactionRepository
import com.example.asmartprintingservice.presentation.historyData.HistoryDataState
import com.example.asmartprintingservice.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _transactionState = MutableStateFlow(TransactionState())
    val transactionState = _transactionState

    fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.getAllTransactions -> {
                getAllTransactions()
            }

            is TransactionEvent.insertTransaction -> {
                insertTransaction(event.transaction)
            }

            is TransactionEvent.deleteTransaction -> {
                deleteTransaction(event.id)
            }
        }
    }

    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()

    private fun getAllTransactions() {
        viewModelScope.launch {
            transactionRepository.getAllTransactions().collect {
                when (it) {
                    is Resource.Loading -> {
                        _transactionState.value = TransactionState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _transactionState.value = TransactionState(transactions = it.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _transactionState.value = TransactionState(msg = it.msg)

                        _snackbarEventFlow.emit(
                            SnackbarEvent.ShowSnackbar(
                                message = "Couldn't get all transactions ${it.msg}",
                                duration = SnackbarDuration.Long
                            )
                        )
                    }
                }
            }
        }
    }
        private fun insertTransaction(transaction: Transaction) {
            viewModelScope.launch {
                transactionRepository.insertTransaction(transaction).collect{
                    when(it){
                        is Resource.Loading -> {
                            _transactionState.value = TransactionState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _transactionState.value = TransactionState(msg = it.data ?: "Success")

                            _snackbarEventFlow.emit(
                                SnackbarEvent.ShowSnackbar(
                                    message = "Mua giấy thành công",
                                    duration = SnackbarDuration.Long
                                )
                            )
                        }

                        is Resource.Error -> {
                            _transactionState.value = TransactionState(msg = it.msg)

                            _snackbarEventFlow.emit(
                                SnackbarEvent.ShowSnackbar(
                                    message = "Couldn't insert transaction with error : ${it.msg}",
                                    duration = SnackbarDuration.Long
                                )
                            )
                        }
                    }
                }
            }
        }

        private fun deleteTransaction(id: Int) {
            viewModelScope.launch {
                transactionRepository.deleteTransaction(id).collect{
                    when(it){
                        is Resource.Loading -> {
                            _transactionState.value = TransactionState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _transactionState.value = TransactionState(msg = it.data ?: "Success")
                        }

                        is Resource.Error -> {
                            _transactionState.value = TransactionState(msg = it.msg)
                        }
                    }
                }
            }
        }
}