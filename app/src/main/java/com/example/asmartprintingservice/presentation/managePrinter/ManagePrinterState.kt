package com.example.asmartprintingservice.presentation.managePrinter

import com.example.asmartprintingservice.data.model.PrinterDTO

data class ManagePrinterState (
    val isLoading : Boolean = false,
    val errorMsg : String? = null,
    var printers : List<PrinterDTO> = emptyList(),
    val search : List<PrinterDTO> = emptyList(),
    val url : String? = null,
    val isDelete : Boolean = false,
    var isSearch : Boolean = false,
    var isSelect : Boolean = false,
    val fileCurrentId : Int ?= null
)