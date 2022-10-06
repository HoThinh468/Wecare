package com.vn.wecare.feature.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vn.wecare.ui.theme.iconSize
import com.vn.wecare.ui.theme.normalPadding

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
    ) {
        HomeHeader(modifier = modifier, moveToNotificationScreen = {})
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier,
    moveToNotificationScreen: () -> Unit,
) {
    Row(
        modifier = modifier.padding(normalPadding).fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Hello there", style = MaterialTheme.typography.body1)
            Text(text = "How are you today", style = MaterialTheme.typography.h2)
        }
        IconButton(
            onClick = moveToNotificationScreen,
            modifier = modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}