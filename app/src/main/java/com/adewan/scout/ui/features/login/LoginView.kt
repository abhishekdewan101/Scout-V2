package com.adewan.scout.ui.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import com.adewan.scout.R
import com.adewan.scout.ui.theme.jerseyFont
import com.adewan.scout.ui.theme.poppinsFont
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginView(viewModel: LoginViewModel = koinViewModel()) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.loginbackgroundrotated),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(1.75f),
            contentScale = ContentScale.Crop
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
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Pixel Shelf",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = jerseyFont,
                    fontSize = 64.sp,
                    color = Color.White,
                )
            )

            Text(
                "Discover, explore and get recommended top rated video games releasing everyday",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontFamily = poppinsFont,
                    color = Color.White,
                    fontSize = 14.sp
                ),
            )
            Text(
                "Get Started",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontFamily = poppinsFont,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .clickable {
                        coroutineScope.launch {
                            val response = CredentialManager
                                .create(context)
                                .getCredential(
                                    context = context,
                                    request = viewModel.getCredentialsRequest()
                                )
                            viewModel.processGetCredentialResponse(response)
                        }
                    }
                    .clip(MaterialTheme.shapes.large)
                    .background(
                        brush = Brush.linearGradient(
                            0.0f to Color(0xFF1952c2),
                            0.55f to Color(0xFF252b7d)
                        )
                    )
                    .padding(15.dp)
            )
        }
    }
}

@Composable
@Preview
fun LoginViewPreview() {
    LoginView()
}