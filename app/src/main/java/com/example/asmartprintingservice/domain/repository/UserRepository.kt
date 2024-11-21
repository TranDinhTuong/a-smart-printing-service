package com.example.asmartprintingservice.domain.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserProfile(userId: String): Flow<Resource<UserProfile>>
    suspend fun updateUserProfile(userProfile: UserProfile): Flow<Resource<Unit>>
}
