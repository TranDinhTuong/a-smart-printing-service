package com.example.asmartprintingservice.presentation.user

import com.example.asmartprintingservice.presentation.auth.AuthEvent

sealed class UserEvent {
    //data class getUserInfo(val userId: String) : UserEvent()

    data class onChangeFullName(val fullName: String) : UserEvent()

    data class onChangePhoneNumber(val phoneNumber: String) : UserEvent()

    data object updateFullNameAndPhoneNumber : UserEvent()

}