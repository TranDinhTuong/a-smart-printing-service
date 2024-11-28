package com.example.asmartprintingservice.presentation.auth

import com.example.asmartprintingservice.domain.model.User

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
