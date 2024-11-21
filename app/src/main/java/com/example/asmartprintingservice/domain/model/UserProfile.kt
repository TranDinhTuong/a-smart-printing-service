package com.example.asmartprintingservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String? = null,
    val email: String,
    val full_name: String,
    val phone_number: String,
    val created_at: String
)
