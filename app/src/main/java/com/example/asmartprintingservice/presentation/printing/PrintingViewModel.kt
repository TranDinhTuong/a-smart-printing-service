package com.example.asmartprintingservice.presentation.printing

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.PrinterStatus
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.domain.repository.AuthRepository
import com.example.asmartprintingservice.domain.repository.FileRepository
import com.example.asmartprintingservice.domain.repository.RequestRepository
import com.example.asmartprintingservice.domain.repository.PrinterRepository
import com.example.asmartprintingservice.util.SnackbarEvent
import com.example.asmartprintingservice.util.convertDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrintingViewModel @Inject constructor (
    private val printerRepository: PrinterRepository,
    private val fileRepository: FileRepository,
    private val requestRepository : RequestRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _printingState = MutableStateFlow(PrintingState())
    val printingState: StateFlow<PrintingState> = _printingState

    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()

    init {
        //getPrinter()

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
                val updatedState = _printingState.value.copy(isOneSided = event.isSingleSided)
                updatePaperNeeded(updatedState)
            }

            is PrintingEvent.onChangePrintQuantity -> {
                val updatedState = _printingState.value.copy(printQuantity = event.printQuantity)
                updatePaperNeeded(updatedState)
            }
            is PrintingEvent.getPrinter -> {
                getPrinter()
            }

            is PrintingEvent.onChangeUserId -> {
                _printingState.update {
                    it.copy(userId = event.userId)
                }
                setPaperCurrent()
            }
        }
    }

    private fun setPaperCurrent(){
        viewModelScope.launch {
            authRepository.getUserProfile(printingState.value.userId).collect{userProfile->
                when(userProfile){
                    is Resource.Success -> {
                        _printingState.update {
                            it.copy(paperCurrent = userProfile.data!!.paper)
                        }
                    }
                    else -> { }
                }
            }
        }
    }
    fun getPrinter() {
        viewModelScope.launch {
            printerRepository.getAllPrinter().collect() { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Log.e("PrintingViewModel", "Error fetching printers: ${resource.msg}")
                        _printingState.value = PrintingState().copy(
                            isLoading = false,
                            errorMsg = resource.msg
                        )
                    }

                    is Resource.Loading -> {
                        Log.d("PrintingViewModel", "Loading printers...")
                        _printingState.value = PrintingState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val printers = resource.data?.filter { it.state == PrinterStatus.ON } ?: emptyList()
                        _printingState.value = _printingState.value.copy(
                            isLoading = false,
                            printers = printers // Lưu danh sách máy in vào state
                        )
                    }
                }
            }
        }
    }

    fun getNumPages(fileId: Int, userId: String) {
        viewModelScope.launch {
            fileRepository.getFiles(userId).collect() { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Log.e("PrintingViewModel", "Error fetching files: ${resource.msg}")
                        _printingState.value = PrintingState().copy(
                            isLoading = false,
                            errorMsg = resource.msg
                        )
                    }

                    is Resource.Loading -> {
                        Log.d("PrintingViewModel", "Loading files...")
                        _printingState.value = PrintingState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        var list_files = resource.data ?: emptyList()
                        val file = list_files.find { it.id == fileId }
                        if (file != null) {
                            _printingState.value = _printingState.value.copy(
                                isLoading = false,
                                numPages = file.numberPages // Lưu danh sách máy in vào state
                            )
                            updatePaperNeeded(_printingState.value)
                        }
                    }
                }
            }
        }
    }
    private fun calculatePaperNeeded(numPages : Int, printQuantity: Int, isOneSided: Boolean): Int {
        return if (isOneSided) printQuantity * numPages else ((numPages + 1) / 2)*printQuantity
    }
    fun updatePaperNeeded(updatedState : PrintingState) {
        _printingState.value = updatedState.copy(
            paperNeeded = calculatePaperNeeded(updatedState.numPages, updatedState.printQuantity, updatedState.isOneSided)
        )
    }

    fun saveHistoryData(fileId : Int) {
        when {
            _printingState.value.selectedPrinter == null -> {
                viewModelScope.launch {
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(
                            message = "Lỗi: Vui lòng chọn máy in!",
                            duration = SnackbarDuration.Short
                        )
                    )
                }
            }
            _printingState.value.paperNeeded > printingState.value.paperCurrent -> {
                viewModelScope.launch {
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(
                            message = "Lỗi: Không đủ giấy, vui lòng mua thêm giấy và thử lại!",
                            duration = SnackbarDuration.Short
                        )
                    )
                }
            }
            else -> {
                viewModelScope.launch {
                    requestRepository.saveHistory(
                        HistoryData(
                            paperType = printingState.value.paperType,
                            isColor = printingState.value.isColored,
                            isSingleSided = printingState.value.isOneSided,
                            receiptDate = printingState.value.receiveDate,
                            file_id = fileId,
                            status = false,
                            numberPrints = printingState.value.printQuantity,
                            numberPages = printingState.value.paperNeeded,
                            printer_id = printingState.value.selectedPrinter?.id,
                            userId = printingState.value.userId
                        )
                    ).collect {
                        when (it) {
                            is Resource.Error -> {
                                _printingState.value = _printingState.value.copy(errorMsg = it.msg)
                                _snackbarEventFlow.emit(
                                    SnackbarEvent.ShowSnackbar(
                                        message = _printingState.value.errorMsg.toString(),
                                        duration = SnackbarDuration.Short
                                    )
                                )
                            }

                            is Resource.Loading -> {
                                _printingState.value = _printingState.value.copy(isLoading = true)
                            }

                            is Resource.Success -> {
                                _printingState.value =
                                    _printingState.value.copy(message = it.data ?: "")
                                _snackbarEventFlow.emit(
                                    SnackbarEvent.ShowSnackbar(
                                        message = "Gửi yêu cầu thành công!",
                                        duration = SnackbarDuration.Long
                                    )
                                )
                                delay(1000)
                                _snackbarEventFlow.emit(
                                    SnackbarEvent.NavigateUp
                                )
                                getPrinter()
                            }
                        }
                    }

                    authRepository.updatePagerCurrent(
                        userId = printingState.value.userId,
                        paperCurrent = printingState.value.paperCurrent - printingState.value.paperNeeded
                    ).collect{
                        when(it){
                            is Resource.Error -> {
                                _snackbarEventFlow.emit(
                                    SnackbarEvent.ShowSnackbar(
                                        message = "update that bai ${it.msg}",
                                        duration = SnackbarDuration.Short
                                    )
                                )
                            }
                            is Resource.Loading -> {
                                _printingState.value = _printingState.value.copy(isLoading = true)
                            }
                            is Resource.Success -> {
                                _snackbarEventFlow.emit(
                                    SnackbarEvent.ShowSnackbar(
                                        message = "Cập nhật giấy thành công!",
                                        duration = SnackbarDuration.Long
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
