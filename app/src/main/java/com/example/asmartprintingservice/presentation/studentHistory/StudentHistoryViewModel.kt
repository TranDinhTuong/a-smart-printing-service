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
import com.example.asmartprintingservice.presentation.studentHistory.HistoryWithStudentName
import com.example.asmartprintingservice.presentation.studentHistory.StudentHistoryEvent
import com.example.asmartprintingservice.presentation.studentHistory.StudentHistoryState
import com.example.asmartprintingservice.util.convertDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentHistoryViewModel @Inject constructor(
    private val historyDataRepository: HistoryDataRepository,
    private val authRepository: AuthRepository,
    private val fileRepository: FileRepository
) : ViewModel()
{

    private val _studentHistoryState = MutableStateFlow(StudentHistoryState())
    val studentHistoryState = _studentHistoryState

    fun onEvent(event: StudentHistoryEvent) {
        when (event) {
            is StudentHistoryEvent.getAllStudentHistory -> {
                getAllHistoryData()
            }

            is StudentHistoryEvent.onSerarchStudentHistory -> {
                //_studentHistoryState.update {
                    //it.copy(searchList = studentHistoryState.value.histories.filter {
                            //it -> it.File?.name?.contains(event.searchQuery, ignoreCase = true) ?: false
                    //}, isSearch = true)
                //}
            }
        }
    }

    private fun getAllHistoryData() {
        viewModelScope.launch {
            historyDataRepository.getAllHistoryData().collect {
                when (it) {
                    is Resource.Error -> {
                        _studentHistoryState.value = StudentHistoryState().copy(errorMsg = it.msg)
                    }

                    is Resource.Loading -> {
                        _studentHistoryState.value = StudentHistoryState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        var listDTO = it.data ?: emptyList()
                        val listWithNames = listDTO.map { historyItem ->
                            val studentName = authRepository.getUserProfile(historyItem.user_id ?: "")
                                .firstOrNull { it is Resource.Success }
                                ?.let { (it as Resource.Success).data?.full_name } ?: "Unknown"
                            HistoryWithStudentName(
                                id = historyItem.id,
                                studentName = studentName,
                                status = historyItem.status,
                                receiptDate = historyItem.receiptDate,
                                paperType = historyItem.paperType,
                                numberPages = historyItem.numberPages,
                            )
                        }

                        _studentHistoryState.value = StudentHistoryState().copy(
                            isLoading = false,
                            histories = listWithNames
                        )

                    }
                }
            }
        }
    }
}