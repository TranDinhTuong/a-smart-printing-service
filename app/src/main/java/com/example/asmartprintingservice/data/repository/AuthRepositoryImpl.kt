package com.example.asmartprintingservice.data.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.UserProfile
import com.example.asmartprintingservice.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : AuthRepository {

    private val auth: Auth = client.auth

    override suspend fun signIn(email: String, password: String): Flow<Resource<UserProfile>> = flow {
        emit(Resource.Loading())
        println("Email: $email, Password: $password")
        auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        val user = auth.retrieveUserForCurrentSession()
//        val userProfile = getUserProfile(user!!.id).first().data
        var userProfile: UserProfile? = null
        getUserProfile(user!!.id).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    userProfile = resource.data
                }
                else -> {}
            }
        }
        emit(Resource.Success(userProfile))
    }.catch {e ->
        emit(Resource.Error(e.message ?: "Unknown error occurred"))
        println(e.message)
    }

    override suspend fun signUp(email: String, password: String, fullName: String, phoneNumber: String, role: String): Flow<Resource<UserProfile>> = flow {
        emit(Resource.Loading())
        val response = auth.signUpWith(Email) {
            this.email = email
            this.password = password
            this.data = buildJsonObject {
                put("full_name", fullName)
                put("phone", phoneNumber)
                put("role", role)
            }
        }
        val user = auth.retrieveUserForCurrentSession()
        val userProfile = getUserProfile(user!!.id).first().data
        emit(Resource.Success(userProfile))
    }.catch {e ->
        emit(Resource.Error(e.message ?: "Unknown error occurred"))
        println(e.message)
    }

    override suspend fun signOut(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            auth.signOut()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun isUserAuthenticated(): Boolean {
        return auth.currentUserOrNull() != null
    }

    override fun getCurrentUser(): UserInfo? {
        return auth.currentUserOrNull()
    }

    override suspend fun getUserProfile(userId: String): Flow<Resource<UserProfile>> = flow {
        if (!isUserAuthenticated()) {
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
            emit(Resource.Success(response))
        } catch (e: Exception) {
            println(e.message)
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override suspend fun updateUserProfile(userProfile: UserProfile): Flow<Resource<Unit>> = flow {
        if (!isUserAuthenticated()) {
            emit(Resource.Error("User is not authenticated"))
            return@flow
        }
        emit(Resource.Loading())
        client.from("User").upsert(userProfile)
        emit(Resource.Success(Unit))
    }
}
