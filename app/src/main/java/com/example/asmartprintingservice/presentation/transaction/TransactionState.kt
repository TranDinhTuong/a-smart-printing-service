package com.example.asmartprintingservice.presentation.transaction

import com.example.asmartprintingservice.data.model.TransactionDTO

data class TransactionState (
    val isLoading : Boolean = false,
    val transactions : List<TransactionDTO> = emptyList(),
    val msg : String? = null,

    //val paper : Int = 0, // số trạng người dùng mua
    val paperCurrent : Int = 100 // số trang hiện tại của người dùng
)