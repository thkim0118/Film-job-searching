package com.fone.filmone.di

import com.fone.filmone.data.repository.AuthRepositoryImpl
import com.fone.filmone.data.repository.CompetitionsRepositoryImpl
import com.fone.filmone.data.repository.HomeRepositoryImpl
import com.fone.filmone.data.repository.ImageUploadRepositoryImpl
import com.fone.filmone.data.repository.InquiryRepositoryImpl
import com.fone.filmone.data.repository.JobOpeningsRepositoryImpl
import com.fone.filmone.data.repository.ProfilesRepositoryImpl
import com.fone.filmone.data.repository.SmsRepositoryImpl
import com.fone.filmone.data.repository.UserRepositoryImpl
import com.fone.filmone.domain.repository.AuthRepository
import com.fone.filmone.domain.repository.CompetitionsRepository
import com.fone.filmone.domain.repository.HomeRepository
import com.fone.filmone.domain.repository.ImageUploadRepository
import com.fone.filmone.domain.repository.InquiryRepository
import com.fone.filmone.domain.repository.JobOpeningsRepository
import com.fone.filmone.domain.repository.ProfilesRepository
import com.fone.filmone.domain.repository.SmsRepository
import com.fone.filmone.domain.repository.UserRepository
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

    @Singleton
    @Binds
    abstract fun bindsHomeRepository(
        repository: HomeRepositoryImpl
    ): HomeRepository
}
