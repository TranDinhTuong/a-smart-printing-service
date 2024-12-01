package com.example.asmartprintingservice.presentation.studentHistory

import com.example.asmartprintingservice.presentation.printing.PrintingEvent

sealed class StudentHistoryEvent{
    data object getAllStudentHistory : StudentHistoryEvent()

    data class onSerarchStudentHistory(val searchQuery: String) : StudentHistoryEvent()

    data class onFilterDateStudentHistory(val startDate: String, val endDate: String) : StudentHistoryEvent()

    data class onChangeStartDate(val date: String) : StudentHistoryEvent()

    data class onChangeEndDate(val date: String) : StudentHistoryEvent()
}