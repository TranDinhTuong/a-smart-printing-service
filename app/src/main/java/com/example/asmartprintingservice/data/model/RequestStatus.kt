package com.example.asmartprintingservice.data.model

enum class RequestStatus(val status: String) {
    ON_PROCESS("ON_PROCESS"),
    COMPLETE("COMPLETE");

    companion object {
        // Hàm tiện ích để lấy Status từ string
        fun fromString(value: String): RequestStatus? {
            return RequestStatus.entries.find { it.status.equals(value, ignoreCase = true) }
        }
    }
}