package com.example.asmartprintingservice.data.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.UserProfile
import com.example.asmartprintingservice.domain.repository.AuthRepository
import com.example.asmartprintingservice.domain.repository.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val client: SupabaseClient, private val authRepository: AuthRepository) : UserRepository {
    override suspend fun getUserProfile(userId: String): Flow<Resource<UserProfile>> = flow {
        if (!authRepository.isUserAuthenticated()) {
            emit(Resource.Error("User is not authenticated"))
            return@flow
        }
        emit(Resource.Loading())
        try {
            val response = client.from("User").select {
                filter {
                    eq("id", userId)
                }
            }.decodeList<UserProfile>().first()
            println(response.full_name)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            println(e.message)
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "An unexpected error occurred"))
    }

    override suspend fun updateUserProfile(userProfile: UserProfile): Flow<Resource<Unit>> = flow {
        if (!authRepository.isUserAuthenticated()) {
            emit(Resource.Error("User is not authenticated"))
            return@flow
        }
        emit(Resource.Loading())
        client.from("User").upsert(userProfile)
        emit(Resource.Success(Unit))
    }.catch { e ->
        emit(Resource.Error(e.message ?: "An unexpected error occurred"))
    }
}