package com.fone.filmone.di

import com.fone.filmone.BuildConfig
import com.fone.filmone.data.datasource.remote.ImageUploadApi
import com.fone.filmone.data.datasource.remote.InquiryApi
import com.fone.filmone.data.datasource.remote.SmsApi
import com.fone.filmone.data.datasource.remote.UserApi
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
    private const val connectionTime = 30_000L
    private const val baseUrl = "http://3.38.94.181/api/"
    private const val smsBaseUrl = "https://54d38vfw3c.execute-api.ap-northeast-1.amazonaws.com/"
    private const val imageUploadBaseUrl =
        "https://rho64ux05h.execute-api.ap-northeast-2.amazonaws.com/"

    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(connectionTime, TimeUnit.MILLISECONDS)
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
    }.build()

    @SmsRetrofit
    @Provides
    fun provideSmsRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(smsBaseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @ImageUploadRetrofit
    @Provides
    fun provideImageUploadRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(imageUploadBaseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideSmsApi(@SmsRetrofit retrofit: Retrofit): SmsApi =
        retrofit.create(SmsApi::class.java)

    @Singleton
    @Provides
    fun provideImageUploadApi(@ImageUploadRetrofit retrofit: Retrofit): ImageUploadApi =
        retrofit.create(ImageUploadApi::class.java)

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Singleton
    @Provides
    fun provideInquiryApi(retrofit: Retrofit): InquiryApi =
        retrofit.create(InquiryApi::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SmsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageUploadRetrofit