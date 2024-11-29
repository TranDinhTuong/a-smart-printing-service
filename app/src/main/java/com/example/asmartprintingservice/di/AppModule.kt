package com.example.asmartprintingservice.di

import com.example.asmartprintingservice.data.repository.AuthRepositoryImpl
import com.example.asmartprintingservice.data.repository.FileRepositoryImpl
import com.example.asmartprintingservice.data.repository.HistoryDataRepositoryImpl
import com.example.asmartprintingservice.domain.repository.AuthRepository
import com.example.asmartprintingservice.domain.repository.FileRepository
import com.example.asmartprintingservice.domain.repository.HistoryDataRepository
import com.example.asmartprintingservice.data.repository.PrinterRepositoryImpl

import com.example.asmartprintingservice.domain.repository.PrinterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
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
            supabaseUrl = "https://ncfcjtzzyksnfuzbucll.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5jZmNqdHp6eWtzbmZ1emJ1Y2xsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzA4MTA2MjcsImV4cCI6MjA0NjM4NjYyN30.-GK2Wr9BsIUj6LztsrcjcW1_2dKEalqJw_1Aw6zpXzo"
        ) {
            install(Postgrest)
            install(Storage)
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
    fun provideHistoryDataRepository(client: SupabaseClient): HistoryDataRepository {
        return HistoryDataRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun providePrinterRepository(client: SupabaseClient): PrinterRepository {
        return PrinterRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(client: SupabaseClient): AuthRepository {
        return AuthRepositoryImpl(client)
    }
}