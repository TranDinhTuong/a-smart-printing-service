package com.example.asmartprintingservice.presentation.file

import com.example.asmartprintingservice.data.model.FileDTO

data class FileState (
    val isLoading : Boolean = false,
    val errorMsg : String? = null,
    val files : List<FileDTO>? = null,
    val url : String? = null,
    val isDelete : Boolean = false,
    val isUpload : Boolean = false,

    val fileCurrentId : Int ?= null
)