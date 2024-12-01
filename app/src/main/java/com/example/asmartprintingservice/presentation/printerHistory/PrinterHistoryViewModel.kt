package com.example.asmartprintingservice.presentation.historyData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.CountRequest
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.domain.repository.AuthRepository
import com.example.asmartprintingservice.domain.repository.FileRepository
import com.example.asmartprintingservice.domain.repository.HistoryDataRepository
import com.example.asmartprintingservice.presentation.PrinterHistory.PrinterHistoryEvent
import com.example.asmartprintingservice.presentation.studentHistory.PrinterHistoryState
import com.example.asmartprintingservice.util.convertDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PrinterHistoryViewModel @Inject constructor(
    private val historyDataRepository: HistoryDataRepository,
) : ViewModel()
{

    private val _printerHistoryState = MutableStateFlow(PrinterHistoryState())
    val printerHistoryState = _printerHistoryState


    fun onEvent(event: PrinterHistoryEvent) {
        when (event) {
            is PrinterHistoryEvent.getAllPrinterHistory -> {
                getAllHistoryData()
            }

            is PrinterHistoryEvent.onSerarchPrinterHistory -> {
                if (event.searchQuery == "") {
                    _printerHistoryState.update {
                        it.copy(isSearch = false)
                    }
                }
                else {
                    _printerHistoryState.update {
                        it.copy(searchList = _printerHistoryState.value.histories.filter { it ->
                            it.Printer?.name?.contains(event.searchQuery, ignoreCase = true)
                                ?: false
                        }, isSearch = true)
                    }
                }
            }

            is PrinterHistoryEvent.onFilterDatePrinterHistory -> {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val sDate = LocalDate.parse(_printerHistoryState.value.startDate, formatter)
                val eDate = LocalDate.parse(_printerHistoryState.value.endDate, formatter)
                if (!_printerHistoryState.value.isSearch) {
                    _printerHistoryState.update {
                        it.copy(filteredDateList = _printerHistoryState.value.histories.filter { it ->
                            val date = LocalDate.parse(it.receiptDate, formatter)
                            date.isAfter(sDate!!.minusDays(1)) && date.isBefore(eDate!!.plusDays(1))
                        })
                    }
                }
                else {
                    _printerHistoryState.update {
                        it.copy(searchList = _printerHistoryState.value.searchList.filter { it ->
                            val date = LocalDate.parse(it.receiptDate, formatter)
                            date.isAfter(sDate!!.minusDays(1)) && date.isBefore(eDate!!.plusDays(1))
                        })
                    }
                }
            }
            is PrinterHistoryEvent.onChangeStartDate -> {
                _printerHistoryState.update {
                    it.copy(startDate = convertDateString(event.date))
                }
            }
            is PrinterHistoryEvent.onChangeEndDate -> {
                _printerHistoryState.update {
                    it.copy(endDate = convertDateString(event.date))
                }
            }
        }
    }

    private fun getAllHistoryData() {
        viewModelScope.launch {
            historyDataRepository.getAllHistoryData().collect {
                when (it) {
                    is Resource.Error -> {
                        _printerHistoryState.value = PrinterHistoryState().copy(errorMsg = it.msg)
                    }

                    is Resource.Loading -> {
                        _printerHistoryState.value = PrinterHistoryState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _printerHistoryState.value = _printerHistoryState.value.copy(
                            isLoading = false,
                            histories = it.data ?: emptyList()
                        )
                        onEvent(PrinterHistoryEvent.onFilterDatePrinterHistory(
                            _printerHistoryState.value.startDate, _printerHistoryState.value.endDate)
                        )
                    }
                }
            }
        }
    }
}