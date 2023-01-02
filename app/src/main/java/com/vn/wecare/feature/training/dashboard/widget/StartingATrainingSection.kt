package com.vn.wecare.feature.training.dashboard.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.ui.theme.Black900

@Composable
fun StartingATrainingSection(
    modifier: Modifier,
    moveToWalkingScreen: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .height(110.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = modifier.padding(top = 16.dp, start = 16.dp),
                text = "Start Your Training Today",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Black900
            )
            TrainingChosen(modifier = modifier, moveToWalkingScreen)
        }
    }
}

@Composable
fun TrainingChosen(
    modifier: Modifier,
    moveToWalkingScreen: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(
                top = 12.dp,
                bottom = 12.dp)
            .fillMaxWidth()
    ) {
        TrainingChosenItem(
            onClick = moveToWalkingScreen,
            icon = Icons.Default.DirectionsWalk,
            description = "Icon Walk"
        )
        TrainingChosenItem(
            onClick = { /*TODO*/ },
            icon = Icons.Default.DirectionsRun,
            description = "Icon Run"
        )
        TrainingChosenItem(
            onClick = { /*TODO*/ },
            icon = Icons.Default.DirectionsBike,
            description = "Icon Bike"
        )
        TrainingChosenItem(
            onClick = { /*TODO*/ },
            icon = Icons.Default.SelfImprovement,
            description = "Meditation"
        )
    }
}

@Composable
fun TrainingChosenItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    description: String
) {
    Box(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .clip(shape = CircleShape)
            .background(MaterialTheme.colors.primary)
            .height(50.dp)
            .width(50.dp)
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = Color.White
            )

        }
    }
}