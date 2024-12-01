package com.example.asmartprintingservice.presentation.studentHistory

sealed class StudentHistoryEvent{
    data object getAllStudentHistory : StudentHistoryEvent()

    data class onSerarchStudentHistory(val searchQuery: String) : StudentHistoryEvent()
}