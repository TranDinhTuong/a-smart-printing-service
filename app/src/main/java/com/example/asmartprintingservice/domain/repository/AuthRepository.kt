package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.UserProfile
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Flow<Resource<UserProfile>>
    suspend fun signUp(email: String, password: String, fullName: String, phoneNumber: String, role: String): Flow<Resource<UserProfile>>
    suspend fun signOut(): Flow<Resource<Unit>>
    fun isUserAuthenticated(): Boolean
    fun getCurrentUser(): UserInfo?
    suspend fun getUserProfile(userId: String): Flow<Resource<UserProfile>>

    suspend fun updatePagerCurrent(userId: String, paperCurrent: Int): Flow<Resource<String>>
    suspend fun updateUserProfile(userProfile: UserProfile): Flow<Resource<Unit>>
}
