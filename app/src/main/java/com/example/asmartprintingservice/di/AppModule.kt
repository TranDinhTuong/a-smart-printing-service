package com.example.asmartprintingservice.di

import com.example.asmartprintingservice.BuildConfig
import com.example.asmartprintingservice.data.repository.AuthRepositoryImpl
import com.example.asmartprintingservice.data.repository.FileRepositoryImpl
import com.example.asmartprintingservice.data.repository.RequestRepositoryImpl
import com.example.asmartprintingservice.data.repository.PrinterRepositoryImpl
import com.example.asmartprintingservice.data.repository.SettingsRepositoryImpl
import com.example.asmartprintingservice.data.repository.TransactionRepositoryImpl
import com.example.asmartprintingservice.data.repository.UserRepositoryImpl
import com.example.asmartprintingservice.domain.repository.AuthRepository
import com.example.asmartprintingservice.domain.repository.FileRepository
import com.example.asmartprintingservice.domain.repository.RequestRepository
import com.example.asmartprintingservice.domain.repository.PrinterRepository
import com.example.asmartprintingservice.domain.repository.SettingsRepository
import com.example.asmartprintingservice.domain.repository.TransactionRepository
import com.example.asmartprintingservice.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        val supabase = createSupabaseClient(
            supabaseUrl = BuildConfig.BASE_URL,
            supabaseKey = BuildConfig.API_KEY
        ) {
            install(Postgrest)
            install(Storage)
            install(Auth)
        }
        return supabase
    }

    @Provides
    @Singleton
    fun provideFileRepository(client: SupabaseClient): FileRepository {
        return FileRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun provideHistoryDataRepository(client: SupabaseClient): RequestRepository {
        return RequestRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun providePrinterRepository(client: SupabaseClient): PrinterRepository {
        return PrinterRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun provideUserRepository(client: SupabaseClient): UserRepository {
        return UserRepositoryImpl(client, AuthRepositoryImpl(client))
    }

    @Provides
    @Singleton
    fun provideAuthRepository(client: SupabaseClient): AuthRepository {
        return AuthRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(client: SupabaseClient): SettingsRepository {
        return SettingsRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(client: SupabaseClient): TransactionRepository {
        return TransactionRepositoryImpl(client)
    }
}