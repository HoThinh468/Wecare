package com.vn.wecare.feature.training.ui.walking.ui

import android.graphics.Paint.Align
import android.widget.NumberPicker
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.vn.wecare.feature.training.ui.dashboard.TopBar
import com.vn.wecare.feature.training.ui.walking.ui.widget.NumberPickerSpinner
import com.vn.wecare.feature.training.ui.walking.ui.widget.TargetChosen


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
        content = {
            Column(
                modifier
            ) {
                TargetChosen(modifier = modifier)
            }
        }
    )
}

