package com.example.asmartprintingservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val id: Int,
    val paper_to_add: Int,
    val next_date_add_paper: String
)