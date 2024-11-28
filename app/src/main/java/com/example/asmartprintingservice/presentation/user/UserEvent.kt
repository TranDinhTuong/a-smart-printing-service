package com.example.asmartprintingservice.presentation.user

import com.example.asmartprintingservice.domain.model.UserProfile

sealed class UserEvent {
    data class GetUserProfile(val userId: String) : UserEvent()
    data class UpdateUserProfile(val userProfile: UserProfile) : UserEvent()
}
