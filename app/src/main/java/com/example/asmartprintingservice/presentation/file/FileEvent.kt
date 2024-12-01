package com.example.asmartprintingservice.presentation.file

import com.example.asmartprintingservice.domain.model.File
import com.example.asmartprintingservice.presentation.historyData.HistoryDataEvent

sealed class FileEvent {

    data class LoadFiles(val userId : String) : FileEvent()
    data class SaveFile(val file : File) : FileEvent()
    data class UploadFile(val name : String, val byteArray: ByteArray) : FileEvent()
    data class DeleteFile(val id : Int) : FileEvent()

    data class onChangeCurrentFileId(val currentId: Int) : FileEvent()
}