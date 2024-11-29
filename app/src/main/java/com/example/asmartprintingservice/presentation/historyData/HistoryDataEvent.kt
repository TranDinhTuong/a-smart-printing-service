package com.example.asmartprintingservice.presentation.historyData

import com.example.asmartprintingservice.domain.model.HistoryData

sealed class HistoryDataEvent{

    data object getAllHistoryData : HistoryDataEvent()

    data object countHistoryDataByPrinter : HistoryDataEvent()

    data class saveHistoryData(val fileId : Int) : HistoryDataEvent()

    data class deleteHistoryData(val id: Int) : HistoryDataEvent()

    data class onChangePaperType(val paperType: String) : HistoryDataEvent()

    data class onChangeColor(val isColor: Boolean) : HistoryDataEvent()

    data class onChangeSingleSided(val isSingleSided: Boolean) : HistoryDataEvent()

    data class onChangeReceiptDate(val receiptDate: String) : HistoryDataEvent()

    data class onSerarchHistoryData(val searchQuery: String) : HistoryDataEvent()
}