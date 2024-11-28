package com.example.asmartprintingservice.data.model

enum class PrinterStatus (val status: String){
    ON("ON"),
    OFF("OFF"),
    MAINTENANCE("MAINTENANCE");

    // Hàm tiện ích để lấy status
    fun displayStatus(): String {
        return status
    }

    companion object {
        // Hàm tiện ích để lấy PrintStatus từ string
        fun fromString(value: String): PrinterStatus? {
            return entries.find { it.status.equals(value, ignoreCase = true) }
        }
    }
}