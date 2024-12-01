package com.example.asmartprintingservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AcceptedFileType(
    val settings_id: Int,
    val file_type: String
)
