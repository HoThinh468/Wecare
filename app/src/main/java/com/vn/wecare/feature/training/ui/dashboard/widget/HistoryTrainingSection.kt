package com.vn.wecare.feature.training.ui.dashboard.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.feature.training.utils.UserAction
import com.vn.wecare.feature.training.utils.secondToMinUtil
import com.vn.wecare.feature.training.utils.stringWith2Decimals
import com.vn.wecare.ui.theme.Black900
import com.vn.wecare.ui.theme.Green300
import com.vn.wecare.ui.theme.Grey500

@Composable
fun HistoryTrainingSection(
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 8.dp
            )
            .fillMaxSize(),
        shape = RoundedCornerShape(20.dp),
        elevation = 16.dp
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
        ) {
            HistoryTrainingTitle(modifier = modifier, kcal = 23.1, duration = 200)
            HistoryTrainingItem(
                modifier = modifier,
                action = UserAction.RUNNING,
                time = "16:30",
                kcal = 23.1,
                duration = 200
            )
            HistoryTrainingItem(
                modifier = modifier,
                action = UserAction.RUNNING,
                time = "16:30",
                kcal = 23.1,
                duration = 200
            )
            HistoryTrainingItem(
                modifier = modifier,
                action = UserAction.RUNNING,
                time = "16:30",
                kcal = 23.1,
                duration = 200
            )
            HistoryTrainingItem(
                modifier = modifier,
                action = UserAction.RUNNING,
                time = "16:30",
                kcal = 23.1,
                duration = 200
            )
        }
    }
}

@Composable
fun LeadingIconText(
    modifier: Modifier,
    icon: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "text with leading icon",
            modifier = modifier.padding(horizontal = 4.dp),
            tint = Color.Gray
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun HistoryTrainingItem(
    modifier: Modifier,
    action: UserAction,
    time: String,
    kcal: Double,
    duration: Int
) {
    Row(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = action.actionIcon(),
                contentDescription = "icon leading",
                tint = MaterialTheme.colors.primary
            )
        }
        Column(
            modifier = modifier
                .weight(2f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = action.actionContent(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = time,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Black900
            )
        }
        Spacer(
            modifier = modifier
                .weight(3f)
        )
        Column(
            modifier = modifier
                .padding(end = 16.dp)
                .weight(3f)
        ) {
            LeadingIconText(
                icon = Icons.Default.Whatshot,
                text = "${stringWith2Decimals(kcal)} kcal",
                modifier = modifier
            )
            LeadingIconText(
                icon = Icons.Default.AccessTime,
                text = "${secondToMinUtil(duration)} min",
                modifier = modifier
            )
        }
    }
}

@Composable
fun HistoryTrainingTitle(
    modifier: Modifier,
    kcal: Double,
    duration: Int
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier.weight(1f),
                text = "Today",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Black900
            )
            Spacer(
                modifier = modifier
                    .weight(4f)
            )
            Column(
                modifier = modifier
                    .padding(end = 16.dp)
            ) {
                LeadingIconText(
                    icon = Icons.Default.Whatshot,
                    text = "${stringWith2Decimals(kcal)} kcal",
                    modifier = modifier
                )
                LeadingIconText(
                    icon = Icons.Default.AccessTime,
                    text = "${secondToMinUtil(duration)} min",
                    modifier = modifier
                )
            }
        }
        Box(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(Grey500)
        )
    }
}