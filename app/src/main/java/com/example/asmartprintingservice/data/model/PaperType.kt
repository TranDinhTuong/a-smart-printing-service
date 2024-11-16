package com.example.asmartprintingservice.data.model

enum class PaperType (val paperType : String){
    A0("A0"),
    A1("A1"),
    A2("A2"),
    A3("A3"),
    A4("A4"),
    A5("A5");

    companion object {
        // Hàm tiện ích để lấy PaperType tu String
        fun fromString(value: String): PaperType? {
            return entries.find { it.paperType.equals(value, ignoreCase = true) }
        }
    }
}