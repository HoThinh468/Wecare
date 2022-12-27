package com.vn.wecare.feature.training.ui.walking.walking

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.training.ui.dashboard.TopBar
import com.vn.wecare.feature.training.ui.walking.widget.TargetChosen


@Composable
fun WalkingScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                text = "Walking",
                firstActionIcon = Icons.Default.MusicNote,
                secondActionIcon = Icons.Default.MoreVert
            )
        },
        content = { padding ->
            Column(
                modifier.padding(padding)
            ) {
               // TargetChosen(modifier = modifier)
            }
        }
    )
}

