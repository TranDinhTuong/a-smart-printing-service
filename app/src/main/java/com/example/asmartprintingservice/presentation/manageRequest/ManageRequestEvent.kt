package com.example.asmartprintingservice.presentation.manageRequest

import com.example.asmartprintingservice.data.model.HistoryDataDTO


sealed class ManageRequestEvent {
    data object LoadRequest: ManageRequestEvent()
    data class SearchRequest(val query: String): ManageRequestEvent() // địa chỉ máy in -_-
    data class DeleteRequest(val request: HistoryDataDTO, val id: Int): ManageRequestEvent()
}