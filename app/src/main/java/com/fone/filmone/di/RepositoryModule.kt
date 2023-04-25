package com.fone.filmone.di

import com.fone.filmone.data.repository.*
import com.fone.filmone.domain.repository.*
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
    abstract fun bindsAuthRepository(
        repository: AuthRepositoryImpl
    ): AuthRepository

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

    @Singleton
    @Binds
    abstract fun bindsInquiryRepository(
        repository: InquiryRepositoryImpl
    ): InquiryRepository

    @Singleton
    @Binds
    abstract fun bindsJobOpeningsRepository(
        repository: JobOpeningsRepositoryImpl
    ): JobOpeningsRepository

    @Singleton
    @Binds
    abstract fun bindsCompetitionsRepository(
        repository: CompetitionsRepositoryImpl
    ): CompetitionsRepository

    @Singleton
    @Binds
    abstract fun bindsProfileRepository(
        repository: ProfilesRepositoryImpl
    ): ProfilesRepository
}