package com.fone.filmone.di

import com.fone.filmone.data.repository.SmsRepositoryImpl
import com.fone.filmone.data.repository.UserRepositoryImpl
import com.fone.filmone.domain.repository.sms.SmsRepository
import com.fone.filmone.domain.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindsSmsRepository(
        repository: SmsRepositoryImpl
    ): SmsRepository
}