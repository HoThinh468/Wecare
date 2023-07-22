package com.vn.wecare.feature.exercises.workout_rest

import android.content.Context
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vn.wecare.feature.exercises.media_sound.Player
import com.vn.wecare.feature.exercises.widget.CtDialog
import com.vn.wecare.feature.exercises.widget.ProgressIndicator
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.midRadius
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.tinyPadding

@Composable
@Preview
fun a() {
    WorkoutRestScreen(
        title = "Jumping Jack",
        duration = 12,
        exercise = com.vn.wecare.R.drawable.jumping_jack,
        onNavigateToWorkoutPage = {},
        currentIndex = 5,
        totalIndex = 10,
        context = LocalContext.current,
        onQuit = {}
    )
}

@Composable
fun WorkoutRestScreen(
    modifier: Modifier = Modifier,
    title: String,
    duration: Int,
    exercise: Int,
    onNavigateToWorkoutPage: () -> Unit,
    currentIndex : Int,
    totalIndex : Int,
    onQuit: () -> Unit,
    context: Context
) {

    val player = Player(context)
    var ticks by remember { mutableStateOf(duration) }

    var onResume by remember {
        mutableStateOf(true)
    }

    var openDialog by remember { mutableStateOf(false) }
    if (openDialog) {
        Dialog(onDismissRequest = {
            openDialog = !openDialog
            onResume = !onResume
        }) {
            onResume = false
            CtDialog(
                title = "Are you sure want to quit?",
                message = "You should try to finish your workout today.",
                onDismiss = {
                    openDialog = !openDialog
                    onResume = !onResume
                },
                onAgreeText = "Sure",
                onAgree = {
                    onQuit()
                },
                height = 300.dp
            )
        }
    }

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
                        IconButton(onClick = {
                            openDialog = !openDialog
                        }) {
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

            Box(
                modifier = modifier.weight(2f)
            ) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ProgressIndicator(
                        duration = ticks,
                        modifier = modifier,
                        onNavigationToNext = {
                            onNavigateToWorkoutPage()
                        },
                        player = player
                    )
                    Row(
                        modifier = modifier
                            .height(64.dp)
                            .padding(top = halfMidPadding)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Green500),
                            onClick = {
                                ticks += 20
                            },
                            modifier = modifier
                                .width(84.dp)
                                .clip(RoundedCornerShape(midRadius))
                        ) {
                            Text(
                                text = "+ 20s",
                                modifier = modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = WeCareTypography.button,
                                color = Color.White
                            )
                        }
                        Button(
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Green500),
                            onClick = {
                                onNavigateToWorkoutPage()
                            },
                            modifier = modifier
                                .width(84.dp)
                                .clip(RoundedCornerShape(midRadius))
                        ) {
                            Text(
                                text = "Skip",
                                modifier = modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = WeCareTypography.button,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            Text(
                text = "Next ${currentIndex + 1}/$totalIndex",
                modifier = modifier
                    .padding(start = midPadding)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = WeCareTypography.body1
            )
            Box(
                modifier = modifier.fillMaxWidth().height(6.dp).background(Color.LightGray)
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth((currentIndex + 1) * 1f / totalIndex)
                        .height(6.dp)
                        .background(Green500)
                )
            }
            Text(
                text = title,
                modifier = modifier.fillMaxWidth().padding(top = smallPadding),
                textAlign = TextAlign.Center,
                style = WeCareTypography.h3
            )
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .weight(3f)
                    .padding(vertical = halfMidPadding),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = modifier
                        .fillMaxSize(0.8f),
                    painter = rememberAsyncImagePainter(
                        exercise,
                        imageLoader
                    ),
                    contentDescription = "",
                )
            }
        }

    }
}