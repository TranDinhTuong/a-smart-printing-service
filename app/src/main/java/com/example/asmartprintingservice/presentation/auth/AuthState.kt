package com.example.asmartprintingservice.presentation.auth

import com.example.asmartprintingservice.domain.model.UserProfile

data class AuthState(
    val isLoading: Boolean = false,
    val user: UserProfile? = null,
    val error: String? = null,

    val fullName : String = "",
    val phoneNumber : String = "",
    val email : String = "",
    val paper : Int = 0
)
