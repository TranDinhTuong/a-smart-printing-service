package com.example.asmartprintingservice.presentation.user

import com.example.asmartprintingservice.domain.model.UserProfile

data class UserState(
    val isLoading: Boolean = false,
    val user: UserProfile? = null,
    val error: String? = null,
)
