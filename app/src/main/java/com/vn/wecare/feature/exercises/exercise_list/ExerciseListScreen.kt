package com.vn.wecare.feature.exercises.exercise_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.OfflineBolt
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.core.model.ListExerciseItem
import com.vn.wecare.core.model.listTest
import com.vn.wecare.ui.theme.Black900
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.tinyPadding

@Composable
fun ExerciseListScreen(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    image: Int = R.drawable.endurance_img,
    title: String = "Endurance exercises",
    trackAmount: Int = 18,
    listExercises: Array<ListExerciseItem> = listTest,
    onNavigationProgramDetail: () -> Unit
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
            Image(
                modifier = modifier
                    .fillMaxWidth()
                    .height(180.dp),
                painter = painterResource(id = image),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
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
                        text = title,
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
                            text = "$trackAmount tracks",
                            style = WeCareTypography.body1,
                            color = Black900
                        )
                    }
                    LazyColumn {
                        items(listExercises) { exercise ->
                            ExerciseListItem(
                                exerciseItem = exercise,
                                onNavigationProgramDetail = { onNavigationProgramDetail() }
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
        Image(
            modifier = modifier
                .weight(5f)
                .size(50.dp),
            painter = painterResource(id = exerciseItem.image),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
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
        if (!exerciseItem.isLiked) {
            Icon(Icons.Default.FavoriteBorder, "")
        } else {
            Icon(Icons.Default.Favorite, "", tint = Red400)
        }
    }
}

enum class ExerciseLevel {
    Easy, Medium, Hard
}

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String
) {
    Row(
        modifier = modifier
    ) {
        Icon(icon, "", tint = Green500)
        Text(
            modifier = modifier.padding(start = tinyPadding),
            text = text,
            style = WeCareTypography.caption,
            color = Color.Black
        )
    }
}