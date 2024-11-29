package com.example.asmartprintingservice.presentation.managePrinter

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.PrinterDTO
import com.example.asmartprintingservice.data.model.PrinterStatus
import com.example.asmartprintingservice.domain.model.Printer
import com.example.asmartprintingservice.domain.repository.PrinterRepository
import com.example.asmartprintingservice.presentation.file.FileState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ManagePrinterViewModel @Inject constructor(
    private val printerRepository: PrinterRepository
) : ViewModel() {

    private val _printerState = MutableStateFlow(ManagePrinterState())
    val printerState : StateFlow<ManagePrinterState>
        get() = _printerState

    private val _printerStates = mutableStateMapOf<String, Boolean>()
    val printerStates: Map<String, Boolean> = _printerStates

    // Cập nhật trạng thái của máy in
    fun updatePrinterState(printerId: String, isOn: Boolean) {
        _printerStates[printerId] = isOn
    }

    // Lấy trạng thái của máy in
    fun getPrinterState(printerId: String): Boolean {
        getPrinters()
        Log.d("ManagePrinterViewModel", "getPrinterState Function: ${_printerStates[printerId]}")
        Log.d("ManagePrinterViewModel", "getPrinterState printerID: /$printerId/" )
        return _printerStates[printerId] ?: false
    }

    fun onEvent(event: ManagePrinterEvent) {
        when(event)
        {
            ManagePrinterEvent.LoadPrinters -> {

                getPrinters()
            }
            is ManagePrinterEvent.DeletePrinters ->
            {
                deletePrinter(event.id)
            }

            is ManagePrinterEvent.SearchPrinters -> {
                _printerState.update {
                    it.copy(search = printerState.value.printers.filter {
                            it -> it.address.contains(event.query, ignoreCase = true) ?: false
                    }, isSearch = true)
                }
            }

            is ManagePrinterEvent.UpdatePrinterStatus -> {

                val printer = event.printer
                val check = event.check
                toggle(printer, check)
                Log.d("ManagePrinterViewModel !!!", "UpdatePrinterStatus event triggered for printer: ${event.printer}")
            }

            is ManagePrinterEvent.InsertPrinter -> {
                Log.d("ManagePrinterViewModel", "INSERT_PRINTER_PRE: ${event.printer}")
                val printer = event.printer
                addPrinter(printer)
                Log.d("ManagePrinterViewModel", "INSERT PRINTER: ${event.printer}")
            }

            is ManagePrinterEvent.getPrinterById -> TODO()
        }

    }

    fun getPrinters() {
        Log.d("ManagePrinterViewModel", "getPrinters() called")
        viewModelScope.launch {
            printerRepository.getAllPrinter().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Log.e("ManagePrinterViewModel", "Error fetching printers: ${resource.msg}")
                        _printerState.value = ManagePrinterState().copy(
                            isLoading = false,
                            errorMsg = resource.msg
                        )
                    }
                    is Resource.Loading -> {
                        Log.d("ManagePrinterViewModel", "Loading printers...")
                        _printerState.value = ManagePrinterState().copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        // Log tất cả các printers khi thành công
                        val printers = resource.data ?: emptyList()
                        Log.d("ManagePrinterViewModel", "Fetched ${printers.size} printers")

                        printers.forEach { printer ->
                            _printerStates[printer.id] = (printer.state == PrinterStatus.ON)
                            Log.d("ManagePrinterViewModel", "PrinterX: $printer")
                            Log.d("ManagePrinterViewModel", "Printer_STATE: ${ _printerStates[printer.id]}")
                        }

                        if (printers.isEmpty()) {
                            Log.d("ManagePrinterViewModel", "No printers found")
                        }

                        _printerState.value = ManagePrinterState().copy(
                            isLoading = false,
                            printers = printers // Lưu danh sách máy in vào state nếu cần
                        )
                    }
                }
            }
        }
    }

    fun addPrinter(printer: Printer)
    {
        viewModelScope.launch {
            printerRepository.insertPrinter(printer)
            getPrinters()
        }

    }

    private fun deletePrinter(id: String) {
        viewModelScope.launch {
            printerRepository.deletePrinter(id)
            getPrinters()
        }
    }

    private fun turnOffPrinter(id: String) {
        viewModelScope.launch {
            printerRepository.turnOffPrinter(id)
        }
    }

    private fun turnOnPrinter(id: String) {
        viewModelScope.launch {
            printerRepository.turnOnPrinter(id)
        }
    }

    private fun toggle(printerDTO: PrinterDTO, check: Boolean) {
        viewModelScope.launch {
            try {
                Log.d("ManagePrinterViewModel", "previous state: $printerDTO.state")
                // Kiểm tra trạng thái hiện tại và tạo trạng thái mới
                val newStatus = if (check) PrinterStatus.ON else PrinterStatus.OFF

                // Tạo đối tượng Printer mới với trạng thái ngược lại
                val updatedPrinter = Printer(
                    id = printerDTO.id,
                    name = printerDTO.name,
                    address = printerDTO.address,
                    machineType = printerDTO.machineType,
                    dungTichKhayNap = printerDTO.dungTichKhayNap,
                    dungTichKhayChua = printerDTO.dungTichKhayChua,
                    paperTypes = printerDTO.paperTypes,
                    state = newStatus
                )

                Log.d("ManagePrinterViewModel", "new state: $newStatus")
                // Cập nhật máy in trong cơ sở dữ liệu
                printerRepository.updatePrinter(updatedPrinter)


            } catch (e: Exception) {
                e.printStackTrace()
                // Log hoặc xử lý lỗi tại đây nếu cần
                Log.e("ManagePrinterViewModel", "Error toggling printer: ${e.message}")
            }
        }
    }





}