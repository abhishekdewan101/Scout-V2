package com.adewan.scout.core.auth

import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.adewan.scout.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

enum class AuthenticationState {
    USER_AUTHENTICATED,
    USER_UNAUTHENTICATED
}

class FirebaseAuthenticationRepository(private val auth: FirebaseAuth) {
    private val initialState = if (auth.currentUser != null) {
        AuthenticationState.USER_AUTHENTICATED
    } else {
        AuthenticationState.USER_UNAUTHENTICATED
    }
    private val authenticationState = MutableStateFlow(initialState)

    init {
        auth.addAuthStateListener {
            if (it.currentUser != null) {
                authenticationState.value = AuthenticationState.USER_AUTHENTICATED
            } else {
                authenticationState.value = AuthenticationState.USER_UNAUTHENTICATED
            }
        }
    }

    fun getAuthenticationState(): Flow<AuthenticationState> {
        return authenticationState
    }

    fun getCurrentUser(): FirebaseUser {
        return auth.currentUser
            ?: throw IllegalStateException("Call this only when user is authenticated")
    }

    fun getCredentialRequest(): GetCredentialRequest {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_OAUTH_CREDS)
            .setAutoSelectEnabled(true)
            .build()
        return GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
    }

    fun processCredential(response: GetCredentialResponse) {
        val credential = response.credential
        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)
            val firebaseCredential =
                GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
            auth.signInWithCredential(firebaseCredential)
        }
    }
}