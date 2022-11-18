package com.vn.wecare.feature.training.walking.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.training.dashboard.ui.TopBar
import com.vn.wecare.feature.training.walking.ui.widget.TargetChosen


@Composable
fun WalkingScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                text = "Walking",
                navigateBack = navigateBack,
                firstActionIcon = Icons.Default.MusicNote,
                secondActionIcon = Icons.Default.MoreVert
            )
        },
        content = {
            Column(
                modifier
            ) {
                TargetChosen(modifier = modifier)
            }
        }
    )
}

