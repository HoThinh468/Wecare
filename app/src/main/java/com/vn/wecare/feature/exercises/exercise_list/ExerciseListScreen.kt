package com.vn.wecare.feature.exercises.exercise_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vn.wecare.core.model.ListExerciseItem
import com.vn.wecare.feature.exercises.ExerciseListScreenUI
import com.vn.wecare.ui.theme.*

@Composable
fun ExerciseListScreen(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    uiState: ExerciseListScreenUI,
    onNavigationProgramDetail: (Int) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { onNavigationBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = {  /*TODO*/ }) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = "Information",
                            tint = Color.White
                        )
                    }
                },
                title = { Text("") },
                backgroundColor = Color.Black
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .background(Color.Black)
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Surface(
                modifier = modifier,
                color = Color.White,
                shape = RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)
            ) {
                Column(
                    modifier = modifier
                        .padding(horizontal = midPadding)
                ) {
                    Text(
                        modifier = modifier.padding(top = 20.dp),
                        text = uiState.title,
                        style = WeCareTypography.h3,
                        color = Color.Black
                    )
                    Row(
                        modifier = modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Functions, contentDescription = "", tint = Color.Black)
                        Text(
                            modifier = modifier.padding(start = 10.dp),
                            text = "${uiState.trackAmount} tracks",
                            style = WeCareTypography.body1,
                            color = Black900
                        )
                    }
                    LazyColumn {
                        items(uiState.listExercise) { exercise ->
                            ExerciseListItem(
                                exerciseItem = exercise,
                                onNavigationProgramDetail = {
                                    onNavigationProgramDetail(uiState.listExercise.indexOf(exercise))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseListItem(
    modifier: Modifier = Modifier,
    exerciseItem: ListExerciseItem,
    onNavigationProgramDetail: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onNavigationProgramDetail()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier
                .padding(start = 15.dp)
                .weight(19f)
                .fillMaxHeight()
        ) {
            Text(
                modifier = modifier.padding(bottom = 10.dp),
                text = exerciseItem.title,
                style = WeCareTypography.body1,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextWithIcon(icon = Icons.Default.OfflineBolt, text = exerciseItem.level.toString())
                TextWithIcon(
                    icon = Icons.Default.Timelapse,
                    text = "${exerciseItem.duration} minutes"
                )
                Spacer(modifier = modifier.width(midPadding))
            }
        }
        Spacer(modifier = modifier.weight(4f))
        Icon(Icons.Default.NavigateNext, "")
    }
}

enum class ExerciseLevel {
    Beginner, Intermediate, Advanced
}

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    tint: Color = Green500
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, "", tint = tint)
        Text(
            modifier = modifier.padding(start = tinyPadding),
            text = text,
            style = WeCareTypography.caption,
            color = Color.Black
        )
    }
}