package com.example.asmartprintingservice.data.repository

import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.User
import com.example.asmartprintingservice.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    client: SupabaseClient
) : AuthRepository {

    private val auth: Auth = client.auth

    override suspend fun signIn(email: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        val user = auth.retrieveUserForCurrentSession()
        emit(Resource.Success(User(id = user.id, email = user.email!!)))
    }.catch {e ->
        emit(Resource.Error(e.message ?: "Unknown error occurred"))
        println(e.message)
    }

    override suspend fun signUp(email: String, password: String, fullName: String, phoneNumber: String, role: String): Flow<Resource<User>> = flow {
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
        response?.email?.let { emit(Resource.Success(User(id = response.id, email = it))) }
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

    override fun getCurrentUser(): User? {
        val user = auth.currentUserOrNull()
        return user?.let { it.email?.let { it1 -> User(id = it.id, email = it1) } }
    }
}
