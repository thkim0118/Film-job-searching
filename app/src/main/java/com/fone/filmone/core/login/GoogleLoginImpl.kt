package com.fone.filmone.core.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.fone.filmone.R
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.domain.model.signup.SocialLoginType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

class GoogleLoginImpl(
    override val loginCallback: SNSLoginUtil.LoginCallback,
    private val googleSignInLauncher: ActivityResultLauncher<Intent>
) : SnsLogin {

    override fun login(context: Context) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null && account.idToken != null) {
            loginCallback.onSuccess(account.idToken ?: return, SocialLoginType.GOOGLE)
            return
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_sign_in_client_id))
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)

        signIn(googleSignInClient)
    }

    private fun signIn(googleSignInClient: GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent

        googleSignInLauncher.launch(signInIntent)
    }

    companion object {
        fun getAccessToken(task: Task<GoogleSignInAccount>): String? {
            return try {
                task.result.idToken
            } catch (e: Throwable) {
                LogUtil.e("error : ${e.localizedMessage}")
                null
            }
        }
    }
}