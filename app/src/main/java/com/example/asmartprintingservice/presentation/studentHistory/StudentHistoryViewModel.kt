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
import com.example.asmartprintingservice.presentation.studentHistory.StudentHistoryEvent
import com.example.asmartprintingservice.presentation.studentHistory.StudentHistoryState
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
class StudentHistoryViewModel @Inject constructor(
    private val historyDataRepository: HistoryDataRepository,
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
                if (event.searchQuery == "") {
                    _studentHistoryState.update {
                        it.copy(isSearch = false)
                    }
                }
                else {
                    _studentHistoryState.update {
                        it.copy(searchList = studentHistoryState.value.histories.filter { it ->
                            it.User?.full_name?.contains(event.searchQuery, ignoreCase = true)
                                ?: false
                        }, isSearch = true)
                    }
                }
            }

            is StudentHistoryEvent.onFilterDateStudentHistory -> {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val sDate = LocalDate.parse(_studentHistoryState.value.startDate, formatter)
                val eDate = LocalDate.parse(_studentHistoryState.value.endDate, formatter)
                if (!studentHistoryState.value.isSearch) {
                    _studentHistoryState.update {
                        it.copy(filteredDateList = studentHistoryState.value.histories.filter { it ->
                            val date = LocalDate.parse(it.receiptDate, formatter)
                            date.isAfter(sDate!!.minusDays(1)) && date.isBefore(eDate!!.plusDays(1))
                        })
                    }
                }
                else {
                    _studentHistoryState.update {
                        it.copy(searchList = studentHistoryState.value.searchList.filter { it ->
                            val date = LocalDate.parse(it.receiptDate, formatter)
                            date.isAfter(sDate!!.minusDays(1)) && date.isBefore(eDate!!.plusDays(1))
                        })
                    }
                }
            }
            is StudentHistoryEvent.onChangeStartDate -> {
                _studentHistoryState.update {
                    it.copy(startDate = convertDateString(event.date))
                }
            }
            is StudentHistoryEvent.onChangeEndDate -> {
                _studentHistoryState.update {
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
                        _studentHistoryState.value = StudentHistoryState().copy(errorMsg = it.msg)
                    }

                    is Resource.Loading -> {
                        _studentHistoryState.value = StudentHistoryState().copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _studentHistoryState.value = _studentHistoryState.value.copy(
                            isLoading = false,
                            histories = it.data ?: emptyList()
                        )
                        onEvent(StudentHistoryEvent.onFilterDateStudentHistory(
                            _studentHistoryState.value.startDate, _studentHistoryState.value.endDate)
                        )
                    }
                }
            }
        }
    }
}