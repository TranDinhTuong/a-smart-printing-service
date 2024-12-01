package com.example.asmartprintingservice.presentation.transaction

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.TransactionDTO
import com.example.asmartprintingservice.domain.model.Transaction
import com.example.asmartprintingservice.domain.repository.AuthRepository
import com.example.asmartprintingservice.domain.repository.TransactionRepository
import com.example.asmartprintingservice.presentation.historyData.HistoryDataState
import com.example.asmartprintingservice.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _transactionState = MutableStateFlow(TransactionState())
    val transactionState = _transactionState

    private val _latestTransactions = MutableStateFlow<List<TransactionDTO>>(emptyList())
    val latestTransactions: StateFlow<List<TransactionDTO>> = _latestTransactions

    fun onEvent(event: TransactionEvent) {
        when (event) {

            is TransactionEvent.insertTransaction -> {
                event.transaction.userId?.let {
                    getPaperCurrent(event.transaction.userId, event.transaction.amount)
                    insertTransaction(event.transaction)
                }
            }

            is TransactionEvent.deleteTransaction -> {
                deleteTransaction(event.id)
            }

        }
    }

    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()


    fun fetchTransactions(userId: String) {
        viewModelScope.launch {
            transactionRepository.getTransactionsForLastMonth(userId).collect{
                when (it) {
                    is Resource.Loading -> {
                        _transactionState.value = TransactionState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _transactionState.value =
                            TransactionState(transactions = it.data ?: emptyList())
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

            // Lấy 10 giao dịch gần nhất
            _latestTransactions.value = transactionState.value.transactions.takeLast(10)
        }
    }


    private fun getPaperCurrent(userId : String, paper : Int) {
        viewModelScope.launch {
            authRepository.getUserProfile(userId).collect { userProfile ->
                when (userProfile) {
                    is Resource.Success -> {
                        _transactionState.update {
                            it.copy(paperCurrent = userProfile.data!!.paper)
                        }
                    }
                    else -> {}
                }
            }

            authRepository.updatePagerCurrent(
                userId,
                paperCurrent = transactionState.value.paperCurrent + paper
            ).collect {
                when (it) {
                    is Resource.Success -> {
                        _snackbarEventFlow.emit(
                            SnackbarEvent.ShowSnackbar(
                                message = "Update paper success",
                                duration = SnackbarDuration.Long
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    private fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionRepository.insertTransaction(transaction).collect {
                when (it) {
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

                        delay(1000)

                        _snackbarEventFlow.emit(
                            SnackbarEvent.NavigateUp
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
            transactionRepository.deleteTransaction(id).collect {
                when (it) {
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