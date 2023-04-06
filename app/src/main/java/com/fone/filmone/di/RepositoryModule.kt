package com.fone.filmone.di

import com.fone.filmone.data.repository.ImageUploadRepositoryImpl
import com.fone.filmone.data.repository.SmsRepositoryImpl
import com.fone.filmone.data.repository.UserRepositoryImpl
import com.fone.filmone.domain.repository.imageupload.ImageUploadRepository
import com.fone.filmone.domain.repository.sms.SmsRepository
import com.fone.filmone.domain.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindsUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    abstract fun bindsSmsRepository(
        repository: SmsRepositoryImpl
    ): SmsRepository

    @Singleton
    @Binds
    abstract fun bindsImageUploadRepository(
        repository: ImageUploadRepositoryImpl
    ): ImageUploadRepository
}