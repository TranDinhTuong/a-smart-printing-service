package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Flow<Resource<User>>
    suspend fun signUp(email: String, password: String, fullName: String, phoneNumber: String, role: String): Flow<Resource<User>>
    suspend fun signOut(): Flow<Resource<Unit>>
    fun isUserAuthenticated(): Boolean
    fun getCurrentUser(): User?
}
