package com.vn.wecare.feature.training.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey20

@Composable
fun StartingATrainingSection(
    modifier: Modifier
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
                color = Green500
            )
            TrainingChosen(modifier = modifier)
        }
    }
}

@Composable
fun TrainingChosen(
    modifier: Modifier
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
            onClick = { /*TODO*/ },
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
            .background(Green500)
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