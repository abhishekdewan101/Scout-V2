package com.adewan.scout.ui.features.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import io.ktor.websocket.Frame.Text
import org.koin.compose.koinInject

@Composable
fun MainView(firebaseAuthenticationRepository: FirebaseAuthenticationRepository = koinInject()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { firebaseAuthenticationRepository.logout() }) {
            Text("Log Out")
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView()
}