package com.example.asmartprintingservice.presentation.printing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.repository.PrinterRepository
import com.example.asmartprintingservice.util.convertDateString
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrintingViewModel @Inject constructor (
    private val printerRepository: PrinterRepository
) : ViewModel()
{
    private val _printingState = MutableStateFlow(PrintingState())
    val printingState: StateFlow<PrintingState> = _printingState

    init {
        getPrinter()
    }

    fun onEvent(event: PrintingEvent) {
        when (event) {
            is PrintingEvent.onChangePrinter -> {
                _printingState.update {
                    it.copy(selectedPrinter = event.printer)
                }
            }
            is PrintingEvent.onChangeColor -> {
                _printingState.update {
                    it.copy(isColored = event.isColor)
                }
            }

            is PrintingEvent.onChangePaperType -> {
                _printingState.update {
                    it.copy(paperType = event.paperType)
                }
            }

            is PrintingEvent.onChangeReceiptDate -> {
                _printingState.update {
                    it.copy(receiveDate = convertDateString(event.receiptDate))
                }
            }

            is PrintingEvent.onChangeSingleSided -> {
                _printingState.update {
                    it.copy(isOneSided = event.isSingleSided)
                }
            }

            is PrintingEvent.onChangePrintQuantity -> {
                _printingState.update {
                    it.copy(printQuantity = event.printQuantity)
                }
            }
        }
    }

    private fun getPrinter() {
        viewModelScope.launch {
            printerRepository.getAllPrinter().collect() { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Log.e("ManagePrinterViewModel", "Error fetching printers: ${resource.msg}")
                        _printingState.value = PrintingState().copy(
                            isLoading = false,
                            errorMsg = resource.msg
                        )
                    }
                    is Resource.Loading -> {
                        Log.d("ManagePrinterViewModel", "Loading printers...")
                        _printingState.value = PrintingState().copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val printers = resource.data ?: emptyList()
                        _printingState.value = PrintingState().copy(
                            isLoading = false,
                            printers = printers // Lưu danh sách máy in vào state
                        )
                    }
                }
            }
        }
    }
}

