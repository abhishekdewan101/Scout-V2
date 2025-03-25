package com.adewan.scout.core.auth

import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.adewan.scout.BuildConfig
import com.adewan.scout.core.network.NetworkClient
import com.adewan.scout.core.network.models.IgdbAuthentication
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.Instant

enum class AuthenticationState {
    USER_AUTHENTICATED,
    USER_UNAUTHENTICATED
}

class FirebaseAuthenticationRepository(
    private val auth: FirebaseAuth,
    private val datastore: FirebaseFirestore,
    private val networkClient: NetworkClient,
    private val io: CoroutineDispatcher = Dispatchers.IO
) {
    private val initialState = if (auth.currentUser != null) {
        AuthenticationState.USER_AUTHENTICATED
    } else {
        AuthenticationState.USER_UNAUTHENTICATED
    }
    private val authenticationState = MutableStateFlow(initialState)

    private var accessToken: String? = null
    private val ioScope = CoroutineScope(io)

    init {
        auth.addAuthStateListener {
            if (it.currentUser != null) {
                authenticationState.value = AuthenticationState.USER_AUTHENTICATED
                ioScope.launch { accessToken = getAccessToken() }
            } else {
                authenticationState.value = AuthenticationState.USER_UNAUTHENTICATED
            }
        }
    }

    fun getAuthenticationState(): Flow<AuthenticationState> {
        return authenticationState
    }

    suspend fun getCurrentAccessToken(): String {
        return accessToken ?: getAccessToken()
        ?: throw IllegalStateException("Cannot get access token, this shouldn't happen")
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

    suspend fun getAccessToken(): String? {
        if (auth.currentUser == null) return null

        if (accessToken != null) return accessToken

        val cachedAuthentication =
            datastore.collection("igdbAuth").document("currentAuth").get().await()
                .toObject<IgdbAuthentication>()

        if (cachedAuthentication != null && cachedAuthentication.isValid()) {
            accessToken = cachedAuthentication.accessToken
            return accessToken
        }

        val networkAuth = networkClient.getAuthentication().run {
            this.copy(expiresIn = this.expiresIn + Instant.now().epochSecond)
        }

        val result = datastore.collection("igdbAuth").document("currentAuth").set(networkAuth)

        if (result.isSuccessful) {
            accessToken = networkAuth.accessToken
            return accessToken
        } else {
            return null
        }
    }

    fun logout() {
        auth.signOut()
    }
}

private fun IgdbAuthentication.isValid(): Boolean {
    return this.expiresIn >= Instant.now().epochSecond
}
