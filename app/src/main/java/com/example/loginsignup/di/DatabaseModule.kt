package com.example.loginsignup.di

import com.example.loginsignup.db.DatabaseDao
import com.example.loginsignup.db.RegistrationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(registrationDatabase: RegistrationDatabase): DatabaseDao {
        return registrationDatabase.getNoteDao()
    }
}