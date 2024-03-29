package com.vn.wecare.feature.exercises.program_detail

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.model.ProgramDetailItem
import com.vn.wecare.core.model.listDetailEndurance
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.feature.exercises.exercise_list.ExerciseLevel
import com.vn.wecare.feature.exercises.widget.CountDownTimer1
import com.vn.wecare.feature.exercises.widget.RatingStar
import com.vn.wecare.feature.training.dashboard.widget.ProgressBar
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CustomButton

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    ProgramDetailScreen(
        onNavigationBack = {},
        title = "High intensity full body workout",
        level = ExerciseLevel.Advanced,
        duration = 20,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        onNavigateToRatingScreen = {},
        onStartWorkout = {},
        listExercises = listDetailEndurance
    )
}


@Composable
fun ProgramDetailScreen(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    onNavigateToRatingScreen: () -> Unit,
    onStartWorkout: () -> Unit,
    title: String,
    level: ExerciseLevel,
    duration: Int,
    description: String,
    listExercises: List<ProgramDetailItem>,
    viewModel: ProgramDetailViewModel = hiltViewModel(),
    exerciseViewModel: ExercisesViewModel = hiltViewModel()
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
                title = {
                    Text(
                       text = title,
                        color = Color.White,
                    )
                },
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
                modifier = modifier.fillMaxSize(),
                color = Color.White,
                shape = RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = modifier
                            .background(Grey500)
                            .height(10.dp)
                            .width(100.dp)
                            .padding(top = 32.dp)
                    )
                    Row(
                        modifier = modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row(
                            modifier = modifier
                                .wrapContentSize(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "$duration min",
                                style = WeCareTypography.body1,
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
                                style = WeCareTypography.body1,
                                color = Green500,
                                modifier = modifier.padding(vertical = smallPadding)
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier
                                .clickable {
                                    onNavigateToRatingScreen()
                                }
                                .wrapContentSize(),
                        ) {
                            when (val listReviewResponse = viewModel.reviewListResponse) {
                                is Response.Loading -> ProgressBar()
                                is Response.Error -> println(listReviewResponse.e)
                                is Response.Success -> {
                                    exerciseViewModel.checkIsReview(listReviewResponse.data)
                                    val rating = viewModel.getRating(listReviewResponse.data)
                                    val reviewCount =
                                        viewModel.getReviewCount(listReviewResponse.data)

                                    Row(
                                        modifier = modifier
                                            .wrapContentSize(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        RatingStar(rating = rating)
                                        Text(
                                            modifier = modifier.padding(end = tinyPadding),
                                            text = "($rating/5)",
                                            style = WeCareTypography.caption,
                                            color = YellowStar
                                        )
                                    }
                                    Text(
                                        text = "($reviewCount reviews)",
                                        style = WeCareTypography.caption,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        LazyColumn(
                            modifier = modifier
                                .padding(top = 12.dp, bottom = xxExtraPadding)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            items(listExercises) { exercise ->
                                DetailProgramItem(programItem = exercise)
                            }
                        }
                        CustomButton(
                            text = "LET'S START",
                            onClick = {
                                onStartWorkout()
                            },
                            backgroundColor = Green500,
                            textColor = Color.White,
                            padding = 0.dp,
                            modifier = modifier
                                .padding(vertical = normalPadding, horizontal = largePadding)
                        )
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
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Row(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .weight(5f),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = modifier
                    .fillMaxSize(),
                painter = rememberAsyncImagePainter(
                    programItem.exercise,
                    imageLoader
                ),
                contentDescription = "",
            )
        }
        Column(
            modifier
                .padding(start = 15.dp)
                .weight(8f)
                .fillMaxHeight()
        ) {
            Text(
                modifier = modifier.padding(bottom = 10.dp),
                text = programItem.title,
                style = WeCareTypography.h3,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            CountDownTimer1(
                programItem.duration,
                style = WeCareTypography.body1,
            )
        }
    }
}