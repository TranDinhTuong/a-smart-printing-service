package com.example.asmartprintingservice.presentation.manageRequest

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.domain.repository.HistoryDataRepository
import com.example.asmartprintingservice.domain.repository.PrinterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageRequestViewModel @Inject constructor(
    private val historyDataRepository: HistoryDataRepository
) : ViewModel() {

    private val _requestState = MutableStateFlow(ManageRequestState())
    val requestState : StateFlow<ManageRequestState>
        get() = _requestState

    private val _requestStates = mutableStateMapOf<String, Boolean>()

    fun onEvent(event: ManageRequestEvent)
    {
        when(event)
        {
            is ManageRequestEvent.DeleteRequest ->
            {
                deleteRequest(event.request, event.id);
            }
            ManageRequestEvent.LoadRequest -> {
                loadRequest()
            }
            is ManageRequestEvent.SearchRequest -> TODO()
        }
    }

    fun deleteRequest(request: HistoryDataDTO, id: Int)
    {
        viewModelScope.launch {
            try{
                Log.d("ManageRequestViewModel" , "Change request state to false")
                val updatedRequest = HistoryDataDTO(
                    paperType = request.paperType,
                    isColor = request.isColor,
                    isSingleSided = request.isSingleSided,
                    receiptDate = request.receiptDate,
                    file_id = request.file_id,
                    status = false,
                    printer_id = request.printer_id,
                    id = request.id,
                    File = request.File,
                    userId = request.userId,
                )
                historyDataRepository.updateRequest(updatedRequest)
            }
            catch (e: Exception) {
                e.printStackTrace()
                Log.e("ManageRequestViewModel", "Error change state request: ${e.message}")
            }
        }
    }

    fun loadRequest()
    {
        viewModelScope.launch {
            historyDataRepository.getPendingRequests().collect{ resource ->
                when(resource)
                {
                    is Resource.Error -> {
                        _requestState.value = ManageRequestState().copy(
                            isLoading = false,
                            errorMsg = resource.msg
                        )
                    }
                    is Resource.Loading -> {
                        _requestState.value = ManageRequestState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val requests = resource.data ?: emptyList()
                        _requestState.value = ManageRequestState().copy(
                            isLoading = false,
                            requests = requests
                        )
                    }
                }
            }
        }
    }

}