package com.example.asmartprintingservice.presentation.manageRequest

import com.example.asmartprintingservice.data.model.HistoryDataDTO

data class ManageRequestState (
    val isLoading : Boolean = false,
    val errorMsg : String? = null,
    var requests : List<HistoryDataDTO> = emptyList(),
    val search : List<HistoryDataDTO> = emptyList(),
)