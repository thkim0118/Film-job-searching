package com.fone.filmone.core.login

import android.app.Activity
import android.content.Context
import com.fone.filmone.core.login.model.SnsLoginType
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.zze
import com.google.firebase.ktx.Firebase

class AppleLoginImpl(
    override val loginCallback: SNSLoginUtil.LoginCallback
) : SnsLogin {
    private val provider = OAuthProvider.newBuilder("apple.com").apply {
        addCustomParameter("locale", "ko_KR")
        scopes = mutableListOf("email", "name")
    }

    private val auth = Firebase.auth
    private val pending = auth.pendingAuthResult

    override fun login(context: Context) {
        if (pending != null) {
            pending.addOnSuccessListener {
                val accessToken = (it.credential as? zze)?.idToken
                loginCallback.onSuccess(
                    accessToken ?: return@addOnSuccessListener,
                    SnsLoginType.Apple
                )
            }.addOnFailureListener {
                loginCallback.onFail(it.message ?: it.localizedMessage)
            }
        } else {
            auth.startActivityForSignInWithProvider(
                context as? Activity ?: return,
                provider.build()
            )
                .addOnSuccessListener {
                    val accessToken = (it.credential as? zze)?.idToken
                    loginCallback.onSuccess(
                        accessToken ?: return@addOnSuccessListener,
                        SnsLoginType.Apple
                    )
                }
                .addOnCanceledListener {
                    loginCallback.onCancel()
                }
                .addOnFailureListener {
                    if ((it as? FirebaseAuthException)?.errorCode == "ERROR_WEB_CONTEXT_CANCELED") {
                        loginCallback.onCancel()
                    } else {
                        loginCallback.onFail(it.message ?: it.localizedMessage)
                    }
                }
        }
    }
}