package com.adewan.scout.ui.features.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.adewan.scout.R
import com.adewan.scout.core.auth.AuthenticationState
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.ui.theme.postGuerillaFont
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginView(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToPreferenceSelection: () -> Unit
) {

    val authenticationState by viewModel.authenticationState.collectAsState(initial = AuthenticationState.USER_UNAUTHENTICATED)

    LaunchedEffect(authenticationState) {
        if (authenticationState == AuthenticationState.USER_AUTHENTICATED) {
            // navigate
            navigateToPreferenceSelection()
        }
    }

    LoginViewInternal(
        getCredentialRequest = viewModel::getCredentialRequest,
        processCredentials = viewModel::processCredentials
    )
}

@Composable
private fun LoginViewInternal(
    getCredentialRequest: () -> GetCredentialRequest,
    processCredentials: (GetCredentialResponse) -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.loginbackgroundrotated),
            contentDescription = "Login Background",
            modifier = Modifier
                .fillMaxSize()
                .scale(1.75f),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.linearGradient(
                        0.0f to Color.Transparent,
                        0.5f to Color.Black.copy(alpha = 0.5f),
                        1.0f to Color.Black,
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(275.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Scout",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        fontFamily = postGuerillaFont,
                        color = Color.White,
                    )
                    Text(
                        "Discover, explore and get recommended top rated video games releasing everyday",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        fontFamily = poppinsFont,
                        color = Color.White,
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text("Get Started",
                        fontFamily = poppinsFont,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clickable {
                                scope.launch {
                                    val response = CredentialManager
                                        .create(context)
                                        .getCredential(
                                            context = context,
                                            request = getCredentialRequest()
                                        )
                                    processCredentials(response)
                                }
                            }
                            .clip(MaterialTheme.shapes.medium)
                            .background(
                                brush = Brush.linearGradient(
                                    0.0f to Color(0xFF1952c2),
                                    0.55f to Color(0xFF252b7d)
                                )
                            )
                            .padding(15.dp)
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginViewPreview() {
    LoginViewInternal(
        getCredentialRequest = { GetCredentialRequest.Builder().build() },
        processCredentials = {})
}