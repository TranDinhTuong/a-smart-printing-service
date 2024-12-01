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
                getAllHistoryData()
            }

            is HistoryDataEvent.saveHistoryData -> {
                saveHistoryData(event.fileId)
            }

            is HistoryDataEvent.deleteHistoryData -> {
                deleteHistoryData(event.id)
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

            HistoryDataEvent.countHistoryDataByPrinter -> {
                if(historyDataState.value.histories.isEmpty()){
                    getAllHistoryData()
                }
                _historyDataState.update {
                    it.copy(
                        printerCount = historyDataState.value.histories
                            .filter { !it.status }
                            .groupBy { it.printer_id }
                            .map { (printerId, histories) ->
                                CountRequest(printerId!!, histories.size)
                            }
                    )
                }
            }
        }
    }


    private fun deleteHistoryData(id: Int) {
        viewModelScope.launch {
            historyDataRepository.deleteHistory(id).collect {
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

    private fun saveHistoryData(fileId : Int) {
        viewModelScope.launch {
            historyDataRepository.saveHistory(
                HistoryData(
                    paperType = historyDataState.value.paperType,
                    isColor = historyDataState.value.isColor,
                    isSingleSided = historyDataState.value.isSingleSided,
                    receiptDate = historyDataState.value.receiptDate,
                    file_id = fileId,
                    status = false,
                    printer_id = "1",
                    userId = "0f3a729b-d5d6-4987-b404-54282182c204",
                    numberPages = 1,
                    numberPrints = 1
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

    private fun getAllHistoryData() {
        viewModelScope.launch {
            historyDataRepository.getAllHistoryData().collect {
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