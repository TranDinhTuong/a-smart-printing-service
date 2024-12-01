package com.example.asmartprintingservice.presentation.historyData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.CountRequest
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.domain.repository.HistoryDataRepository
import com.example.asmartprintingservice.util.convertDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDataViewModel @Inject constructor(
    private val historyDataRepository: HistoryDataRepository
) : ViewModel()
{

    private val _historyDataState = MutableStateFlow(HistoryDataState())
    val historyDataState = _historyDataState

    fun onEvent(event: HistoryDataEvent) {
        when (event) {
            is HistoryDataEvent.getAllHistoryData -> {
                getAllHistoryData(event.userId)
            }

            is HistoryDataEvent.saveHistoryData -> {
                saveHistoryData(event.fileId, event.userId)
            }

            is HistoryDataEvent.deleteHistoryData -> {
                deleteHistoryData(event.id)
                getPendingHistoryData()
            }

            is HistoryDataEvent.onChangeColor -> {
                _historyDataState.update {
                    it.copy(isColor = event.isColor)
                }
            }

            is HistoryDataEvent.onChangePaperType -> {
                _historyDataState.update {
                    it.copy(paperType = event.paperType)
                }
            }

            is HistoryDataEvent.onChangeReceiptDate -> {
                _historyDataState.update {
                    it.copy(receiptDate = convertDateString(event.receiptDate))
                }
            }

            is HistoryDataEvent.onChangeSingleSided -> {
                _historyDataState.update {
                    it.copy(isSingleSided = event.isSingleSided)
                }
            }

            is HistoryDataEvent.onSerarchHistoryData -> {
                _historyDataState.update {
                    it.copy(searchList = historyDataState.value.histories.filter {
                            it -> it.File?.name?.contains(event.searchQuery, ignoreCase = true) ?: false
                    }, isSearch = true)
                }
            }


            is HistoryDataEvent.getAllPendingHistoryData -> {
                getPendingHistoryData()
            }
        }
    }


    private fun deleteHistoryData(id: Int) {
        viewModelScope.launch {
            historyDataRepository.deleteHistory(id)
        }
    }

    private fun saveHistoryData(fileId : Int, userId : String) {
        viewModelScope.launch {
            historyDataRepository.saveHistory(
                HistoryData(
                    paperType = historyDataState.value.paperType,
                    isColor = historyDataState.value.isColor,
                    isSingleSided = historyDataState.value.isSingleSided,
                    receiptDate = historyDataState.value.receiptDate,
                    file_id = fileId,
                    userId = userId,
                    numberPrints = 1,
                    numberPages = 1,
                    status = false
                )
            ).collect {
                when (it) {
                    is Resource.Error -> {
                        _historyDataState.value = HistoryDataState().copy(errorMsg = it.msg)
                    }

                    is Resource.Loading -> {
                        _historyDataState.value = HistoryDataState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _historyDataState.value = HistoryDataState().copy(message = it.data ?: "")
                    }
                }
            }
        }
    }

    private fun getAllHistoryData(userId: String) {
        viewModelScope.launch {
            historyDataRepository.getAllHistoryData(userId).collect {
                when (it) {
                    is Resource.Error -> {
                        _historyDataState.value = HistoryDataState().copy(errorMsg = it.msg)
                    }

                    is Resource.Loading -> {
                        _historyDataState.value = HistoryDataState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _historyDataState.value = HistoryDataState(histories = it.data ?: emptyList())
                    }
                }
            }
        }
    }

    private fun getPendingHistoryData() {
        viewModelScope.launch {
            historyDataRepository.getPendingRequests().collect {
                when (it) {
                    is Resource.Error -> {
                        _historyDataState.value = HistoryDataState().copy(errorMsg = it.msg)
                    }

                    is Resource.Loading -> {
                        _historyDataState.value = HistoryDataState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _historyDataState.value = HistoryDataState(histories = it.data ?: emptyList())
                    }
                }
            }
        }
    }
}