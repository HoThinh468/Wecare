package com.vn.wecare.feature.training.dashboard.history.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.feature.training.dashboard.TrainingViewModel
import com.vn.wecare.ui.theme.Black900
import com.vn.wecare.ui.theme.Grey500
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.api.ResourceDescriptor.History
import com.vn.wecare.feature.training.dashboard.history.model.Response
import com.vn.wecare.feature.training.dashboard.history.model.TrainingHistory
import com.vn.wecare.feature.training.dashboard.widget.ProgressBar
import com.vn.wecare.feature.training.utils.*
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun HistoryTrainings(
    viewModel: TrainingViewModel = hiltViewModel(),
    historyContents: @Composable (historyTraining: List<TrainingHistory>) -> Unit
) {
    when (val historyResponse = viewModel.trainingHistoryResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> historyContents(historyResponse.data)
        is Response.Error -> print(historyResponse.e)
    }
}

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
        ) {
            HistoryTrainings(
                historyContents = { histories ->
                    HistoryContents(histories = histories)
                }
            )
        }
    }
}

@Composable
fun HistoryContents(
    modifier: Modifier = Modifier,
    histories: List<TrainingHistory>
) {
    HistoryTrainingTitle(modifier = modifier)
    Box(
        modifier = modifier.height(260.dp)
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
        ) {
            items(
                items = histories.reversed()
            ) { history ->
                HistoryTrainingItem(
                    action = convertStringToUserAction(history.userAction),
                    time = convertLongToTime(history.time),
                    kcal = history.kcal,
                    duration = history.duration,
                    distance = history.distance
                )
            }
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
    modifier: Modifier = Modifier,
    action: UserAction,
    time: String,
    kcal: Double,
    duration: Int,
    distance: Double
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
            LeadingIconText(
                icon = Icons.Default.Route,
                text = "$distance km",
                modifier = modifier
            )
        }
    }
}

@Composable
fun HistoryTrainingTitle(
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = modifier
                .padding(top = 8.dp, bottom = 8.dp),
            text = "History",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Black900
        )
        Box(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(Grey500)
        )
    }
}