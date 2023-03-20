package com.vn.wecare.feature.exercises.program_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.exercises.ExercisesScreen
import com.vn.wecare.feature.exercises.exercise_list.ExerciseLevel
import com.vn.wecare.feature.exercises.exercise_list.ExerciseListItem
import com.vn.wecare.core.model.ListExerciseItem
import com.vn.wecare.core.model.ProgramDetailItem
import com.vn.wecare.core.model.listTest
import com.vn.wecare.core.model.listTestProgramDetail
import com.vn.wecare.feature.exercises.widget.ExpandableText
import com.vn.wecare.feature.exercises.widget.RatingStar
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.YellowStar
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.tinyPadding

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    ProgramDetailScreen(
        onNavigationBack = {},
        isLiked = true,
        title = "High intensity full body workout",
        level = ExerciseLevel.Hard,
        duration = 20,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        rating = 4,
        ratedNumber = 231,
        onNavigateToRatingScreen = {}
    )
}


@Composable
fun ProgramDetailScreen(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    onNavigateToRatingScreen: () -> Unit,
    isLiked: Boolean,
    title: String,
    level: ExerciseLevel,
    duration: Int,
    description: String,
    rating: Int,
    ratedNumber: Int,
    listExercises: Array<ProgramDetailItem> = listTestProgramDetail
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
                    IconButton(onClick = { TODO() /* doSomething() */ }) {
                        if (isLiked) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Like",
                                tint = Red400
                            )
                        } else {
                            Icon(
                                Icons.Default.FavoriteBorder,
                                contentDescription = "Like"
                            )
                        }
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
            Column(
                modifier = modifier
                    .background(Color.Black)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    modifier = modifier.padding(bottom = 10.dp),
                    text = title,
                    style = WeCareTypography.h3,
                    color = Color.White,
                    overflow = TextOverflow.Clip
                )
                Row(
                    modifier = modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$duration min",
                        style = WeCareTypography.caption,
                        color = Green500
                    )
                    Box(
                        modifier = modifier
                            .padding(horizontal = smallPadding)
                            .clip(CircleShape)
                            .size(6.dp)
                            .background(Green500)
                    )
                    Text(
                        text = level.toString(),
                        style = WeCareTypography.caption,
                        color = Green500
                    )
                }
                ExpandableText(
                    text = description,
                    textColor = Color.White
                )
                Box(
                    modifier = modifier
                        .padding(vertical = halfMidPadding)
                        .background(Color.White)
                        .height(2.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = "What others say",
                    style = WeCareTypography.caption,
                    color = Color.White,
                    modifier = modifier.padding(bottom = smallPadding)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .padding(bottom = midPadding)
                        .clickable {
                            onNavigateToRatingScreen()
                        }
                ) {
                    RatingStar(rating = rating)
                    Text(
                        modifier = modifier.padding(end = tinyPadding),
                        text = "($rating/5)",
                        style = WeCareTypography.caption,
                        color = YellowStar
                    )
                    Text(
                        text = "($ratedNumber reviews)",
                        style = WeCareTypography.caption,
                        color = Color.White
                    )
                }
            }
            Surface(
                modifier = modifier,
                color = Color.White,
                shape = RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)
            ) {
                LazyColumn(
                    modifier = modifier.padding(top = 30.dp)
                ) {
                    items(listExercises) { exercise ->
                        DetailProgramItem(programItem = exercise)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailProgramItem(
    modifier: Modifier = Modifier,
    programItem: ProgramDetailItem
) {
    Row(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .weight(5f),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = modifier
                    .size(60.dp),
                painter = painterResource(id = programItem.image),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }
        Column(
            modifier
                .padding(start = 15.dp)
                .weight(19f)
                .fillMaxHeight()
        ) {
            Text(
                modifier = modifier.padding(bottom = 10.dp),
                text = programItem.title,
                style = WeCareTypography.body1,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${programItem.reps} times",
                style = WeCareTypography.caption,
                color = Color.Black
            )
        }
        Text(
            text = "00:15",
            style = WeCareTypography.button,
            color = Green500
        )
    }
}