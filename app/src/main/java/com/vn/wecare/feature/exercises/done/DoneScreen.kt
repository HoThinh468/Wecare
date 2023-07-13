package com.vn.wecare.feature.exercises.done

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vn.wecare.R
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.feature.exercises.history.ExerciseHistoryViewModel
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CustomButton
import com.vn.wecare.utils.common_composable.CustomTextField
import java.text.DecimalFormat


@Composable
fun DoneScreen(
    modifier: Modifier = Modifier,
    onDone: () -> Unit,
    exercisesViewModel: ExercisesViewModel,
    doneViewModel: DoneViewModel = hiltViewModel(),
    exerciseHistoryViewModel: ExerciseHistoryViewModel = hiltViewModel()
) {
    var rating by remember { mutableStateOf(0) }

//    if (isLoading.collectAsState().value) {
//        Box(
//            modifier = modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator()
//        }
//    } else {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
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

        Image(
            modifier = modifier
                .fillMaxSize()
                .weight(4f)
                .padding(vertical = halfMidPadding),
            painter = rememberAsyncImagePainter(
                R.drawable.congra,
                imageLoader
            ),
            contentDescription = "",
        )
        Text(
            "Congratulation!",
            style = WeCareTypography.h3
        )
        Text(
            "You did it!",
            style = WeCareTypography.h3
        )
        if (!exercisesViewModel.isReview.collectAsState().value) {
            Box(
                modifier = modifier
                    .clip(
                        RoundedCornerShape(halfMidPadding)
                    )
                    .fillMaxWidth()
                    .background(Grey500)
            ) {
                Column(
                    modifier = modifier
                        .padding(horizontal = halfMidPadding)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = modifier
                            .padding(vertical = halfMidPadding)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TitleAndNumber(
                            title = "Exercises",
                            number = exercisesViewModel.currentWorkoutList.collectAsState().value.size.toString()
                        )
                        TitleAndNumber(
                            title = "Kcal",
                            number = DecimalFormat("#.##").format(
                                doneViewModel.calculateKcalBurn(
                                    exercisesViewModel.startTime.collectAsState().value,
                                    exercisesViewModel.endTime.collectAsState().value
                                )
                            )
                        )
                        TitleAndNumber(
                            title = "Duration",
                            number = workoutTime(
                                exercisesViewModel.endTime.collectAsState().value -
                                        exercisesViewModel.startTime.collectAsState().value
                            )
                        )
                    }
                    RatingStar(rating = remember { mutableStateOf(rating) })
                    CustomTextField(
                        modifier = modifier
                            .padding(
                                start = halfMidPadding,
                                end = halfMidPadding,
                                bottom = halfMidPadding,
                                top = 0.dp
                            )
                            .fillMaxWidth()
                            .height(120.dp),
                        hint = "Enter you feedback",
                        label = "Feedback",
                        backgroundColor = Color.White,
                        cursorColor = Green500,
                        value = doneViewModel.feedbackContent.collectAsState().value,
                        onValueChange = doneViewModel::setContent,
                        isSingleLine = false
                    )
                }
            }
        }
        Box(
            modifier = modifier.weight(2f),
            contentAlignment = Alignment.Center
        ) {
            CustomButton(
                text = "DONE",
                onClick = {
                    exercisesViewModel.resetWorkoutIndex()
                    if (exercisesViewModel.currentType.value == ExerciseType.FullBody) {
                        doneViewModel.updateListDoneFullBody(exercisesViewModel.currentIndex.value)
                    }
                    doneViewModel.addNewReview(
                        exercisesViewModel.currentType.value,
                        exercisesViewModel.currentIndex.value
                    )
                    exerciseHistoryViewModel.addNewHistory(
                        exercisesViewModel.currentType.value,
                        doneViewModel.calculateKcalBurn(
                            exercisesViewModel.startTime.value,
                            exercisesViewModel.endTime.value
                        ),
                        (exercisesViewModel.endTime.value -
                                exercisesViewModel.startTime.value).toInt() / 1000
                    )
                    onDone()
                },
                backgroundColor = Green500,
                textColor = Color.White,
                padding = 0.dp,
                modifier = modifier
                    .padding(midPadding)
                    .height(40.dp)
            )
        }
//        }
    }
}

@Composable
fun RatingStar(
    rating: MutableState<Int>,
    maxRating: Int = 5,
    viewModel: DoneViewModel = hiltViewModel()
) {
    val selectedColor = YellowStar

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..maxRating) {
            val icon = if (i <= rating.value) {
                Icons.Default.Star
            } else {
                Icons.Default.StarOutline
            }
            val starColor = if (i <= rating.value) selectedColor else null
            IconButton(
                onClick = {
                    rating.value = i
                    viewModel.setRating(i)
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = starColor?.let { ColorFilter.tint(it) }
                )
            }
        }
    }
}

@Composable
fun TitleAndNumber(
    title: String,
    number: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = WeCareTypography.caption)
        Text(text = number, style = WeCareTypography.h3)
    }
}

fun workoutTime(time: Long): String {
    val seconds = (time / 1000) % 60
    val minutes = (time / (1000 * 60) % 60)
    return String.format("%02d:%02d", minutes, seconds)
}