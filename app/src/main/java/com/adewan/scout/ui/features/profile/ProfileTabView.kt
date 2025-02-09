package com.adewan.scout.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adewan.scout.ui.components.Header
import com.adewan.scout.ui.theme.ScoutColors
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.utils.bottomBorder
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileTabView(
    colors: ScoutColors,
    onSearchIconPressed: () -> Unit,
    viewModel: ProfileTabViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundColor)
            .verticalScroll(rememberScrollState()),
    ) {

        Header(
            modifier = Modifier.padding(top = 16.dp),
            title = "Profile",
            contrastColor = colors.contrastColor,
            secondaryIcon = Icons.Default.Search,
            onSecondaryIconClicked = onSearchIconPressed,
        )
        Avatar(initials = "AD", contrastColor = colors.contrastColor)

        Column(verticalArrangement = spacedBy(28.dp)) {
            ActionOption(
                title = "Edit Account",
                icon = Icons.Default.EditNote,
                contrastColor = colors.contrastColor
            )
            ActionOption(
                title = "Send Message",
                icon = Icons.AutoMirrored.Filled.Message,
                contrastColor = colors.contrastColor
            )
            ActionOption(
                title = "Delete Account",
                icon = Icons.Default.Delete,
                contrastColor = colors.contrastColor
            )
            ActionOption(
                title = "Logout",
                icon = Icons.Default.PowerSettingsNew,
                contrastColor = colors.contrastColor,
                onClick = {
                    viewModel.logoutUser()
                }
            )
        }
    }
}

@Composable
fun ActionOption(
    title: String,
    icon: ImageVector,
    contrastColor: Color,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .clickable { onClick?.invoke() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .bottomBorder(1.dp, contrastColor)
            .padding(bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(10.dp)
    ) {
        Icon(icon, "", tint = contrastColor, modifier = Modifier.size(24.dp))
        Text(
            title,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = poppinsFont,
                color = contrastColor
            )
        )
    }
}

@Composable
fun Avatar(initials: String, contrastColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.4f))
        ) {
            Text(
                initials,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.SemiBold,
                    color = contrastColor
                )
            )
        }
    }
}

