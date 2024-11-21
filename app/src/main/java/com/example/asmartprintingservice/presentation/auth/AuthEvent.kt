package com.example.asmartprintingservice.presentation.auth

sealed class AuthEvent {
    data class SignIn(val email: String, val password: String) : AuthEvent()
    data class SignUp(val email: String, val password: String, val fullName: String, val phoneNumber: String) : AuthEvent()
    data object SignOut : AuthEvent()
}
