package com.fone.filmone

import android.app.Application
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FOneApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.kakao_sdk_key))

        initNaverIdLoginSDK()
    }

    private fun initNaverIdLoginSDK() {
        try {
            NaverIdLoginSDK.initialize(
                this,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                getString(R.string.naver_client_name)
            )
        } catch (invalidProtocol: InvalidProtocolBufferException) {
            FirebaseCrashlytics.getInstance().log("NaverIdLoginSDK InvalidProtocolBufferException")
            FirebaseCrashlytics.getInstance()
                .recordException(Exception(":> " + invalidProtocol.message))
        } catch (e: RuntimeException) {
            FirebaseCrashlytics.getInstance().log("NaverIdLoginSDK RuntimeException")
            FirebaseCrashlytics.getInstance().recordException(Exception(":> " + e.message))
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log("NaverIdLoginSDK Exception")
            FirebaseCrashlytics.getInstance().recordException(Exception(":> " + e.message))
        } catch (e: Throwable) {
            FirebaseCrashlytics.getInstance().log("NaverIdLoginSDK Throwable")
            FirebaseCrashlytics.getInstance().recordException(Exception(":> " + e.message))
        }
    }
}
