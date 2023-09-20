package com.fone.filmone.core.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.fone.filmone.R
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.data.datamodel.response.user.LoginType
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
            loginCallback.onSuccess(
                account.idToken ?: return,
                account.email ?: return,
                LoginType.GOOGLE
            )
            return
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_sign_in_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)

        signIn(googleSignInClient)
    }

    private fun signIn(googleSignInClient: GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent

        googleSignInLauncher.launch(signInIntent)
    }

    companion object {
        fun getLoginInfo(task: Task<GoogleSignInAccount>): Pair<String, String>? {
            return try {
                val token = task.result?.idToken ?: throw NullPointerException("AccessToken is null")
                val email = task.result?.email ?: throw NullPointerException("AccessToken is null")
                token to email
            } catch (e: Throwable) {
                LogUtil.e("error : ${e.localizedMessage}")
                null
            }
        }
    }
}
