package com.fone.filmone.di

import com.fone.filmone.BuildConfig
import com.fone.filmone.data.datasource.remote.CompetitionsApi
import com.fone.filmone.data.datasource.remote.HomeApi
import com.fone.filmone.data.datasource.remote.ImageUploadApi
import com.fone.filmone.data.datasource.remote.InquiryApi
import com.fone.filmone.data.datasource.remote.JobOpeningsApi
import com.fone.filmone.data.datasource.remote.ProfilesApi
import com.fone.filmone.data.datasource.remote.SmsApi
import com.fone.filmone.data.datasource.remote.TokenApi
import com.fone.filmone.data.datasource.remote.UserApi
import com.fone.filmone.domain.repository.AuthRepository
import com.fone.filmone.framework.drivers.AuthInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val connectionTime = 10_000L
    private const val baseUrl = "http://3.39.0.194/api/"
    private const val smsBaseUrl = "https://du646e9qh1.execute-api.ap-northeast-2.amazonaws.com/"
    private const val imageUploadBaseUrl =
        "https://du646e9qh1.execute-api.ap-northeast-2.amazonaws.com/"

    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").setLenient().create()

    @Provides
    fun provideAuthInterceptor(authRepository: AuthRepository): AuthInterceptor =
        AuthInterceptor(authRepository = authRepository)

    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(connectionTime, TimeUnit.MILLISECONDS)
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }

            addInterceptor(authInterceptor)
        }.build()

    @AuthOkHttpClient
    @Provides
    fun provideAuthOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(connectionTime, TimeUnit.MILLISECONDS)
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
    }.build()

    @AuthRetrofit
    @Provides
    fun provideAuthRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @SmsRetrofit
    @Provides
    fun provideSmsRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(smsBaseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @ImageUploadRetrofit
    @Provides
    fun provideImageUploadRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(imageUploadBaseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Singleton
    @Provides
    fun provideTokenApi(@AuthRetrofit retrofit: Retrofit): TokenApi =
        retrofit.create(TokenApi::class.java)

    @Singleton
    @Provides
    fun provideSmsApi(@SmsRetrofit retrofit: Retrofit): SmsApi = retrofit.create(SmsApi::class.java)

    @Singleton
    @Provides
    fun provideImageUploadApi(@ImageUploadRetrofit retrofit: Retrofit): ImageUploadApi =
        retrofit.create(ImageUploadApi::class.java)

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Singleton
    @Provides
    fun provideInquiryApi(retrofit: Retrofit): InquiryApi = retrofit.create(InquiryApi::class.java)

    @Singleton
    @Provides
    fun provideJobApi(retrofit: Retrofit): JobOpeningsApi =
        retrofit.create(JobOpeningsApi::class.java)

    @Singleton
    @Provides
    fun provideCompetitionsApi(retrofit: Retrofit): CompetitionsApi =
        retrofit.create(CompetitionsApi::class.java)

    @Singleton
    @Provides
    fun provideProfilesApi(retrofit: Retrofit): ProfilesApi =
        retrofit.create(ProfilesApi::class.java)

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SmsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageUploadRetrofit
