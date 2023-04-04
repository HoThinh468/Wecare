package com.vn.wecare.feature.exercises.workout_rest

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vn.wecare.feature.exercises.widget.ProgressIndicator
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding

@Composable
@Preview
fun a() {
    WorkoutRestScreen(
        title = "Jumping Jack",
        duration = 12,
        exercise = com.vn.wecare.R.drawable.jumping_jack,
        onNavigateToWorkoutPage = {}
    )
}

@Composable
fun WorkoutRestScreen(
    modifier: Modifier = Modifier,
    title: String,
    duration: Int,
    exercise: Int,
    onNavigateToWorkoutPage: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                elevation = halfMidPadding,
                title = {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = modifier.wrapContentSize()
                            )
                        }

                        Text(
                            text = "Taking Rest",
                            modifier = modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                backgroundColor = Color.White
            )
        }
    ) { padding ->
        Column(
            modifier = modifier.padding(padding),
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

            ProgressIndicator(
                duration = duration,
                modifier = modifier
                    .weight(2f),
                onNavigationToNext = {
                    onNavigateToWorkoutPage()
                }
            )
            Text(
                text = title,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = WeCareTypography.h3
            )
            Image(
                modifier = modifier
                    .fillMaxSize()
                    .weight(3f)
                    .padding(vertical = halfMidPadding),
                painter = rememberAsyncImagePainter(
                    exercise,
                    imageLoader
                ),
                contentDescription = "",
            )
        }

    }
}