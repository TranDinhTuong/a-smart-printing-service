package com.example.asmartprintingservice.data.model

data class PrinterDTO(
    val id : Int,
    val name : String,
    val address : String,
    val machineType : String,
    val dungTichKhayNap: Int,
    val dungTichKhayChua: Int,
    val paperTypes: Array<PaperType>, ///// ["A1","A2"]
    val state : PrinterStatus
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PrinterDTO

        return paperTypes.contentEquals(other.paperTypes)
    }

    override fun hashCode(): Int {
        return paperTypes.contentHashCode()
    }
}
