package com.vn.wecare.feature.exercises.workout_page

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.vn.wecare.R
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.feature.exercises.media_sound.Player
import com.vn.wecare.feature.exercises.widget.CtDialog
import com.vn.wecare.feature.exercises.widget.ProgressIndicator
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding

//@Preview
//@Composable
//fun a() {
//    WorkoutPageScreen(
//        onQuit = { /*TODO*/ },
//        title = "Jumping Jack",
//        exercise = R.drawable.jumping_jack,
//        duration = 10,
//        viewModel = hiltViewModel()
//    )
//}

@Composable
fun WorkoutPageScreen(
    modifier: Modifier = Modifier,
    onQuit: () -> Unit,
    title: String,
    duration: Int,
    exercise: Int,
    onNavigateToRest: () -> Unit = {},
    onNavigateToPreviousRest: () -> Unit = {},
    viewModel: ExercisesViewModel,
    context: Context
) {
    var onResume by remember {
        mutableStateOf(true)
    }

    var player = Player(context)


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
                elevation = midPadding,
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
                            text = title,
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
                    if (SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()

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
            ProgressIndicator(
                duration = duration,
                modifier = modifier
                    .weight(1f),
                onPlay = onResume,
                onNavigationToNext = {
                    onNavigateToRest()
                },
                player = player
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                    player.stopSound()
                    onNavigateToPreviousRest()
                }) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = modifier.size(40.dp)
                    )
                }
                IconButton(onClick = {
                    onResume = !onResume
                    if (onResume) {
                        player.playSound()
                    } else {
                        player.stopSound()
                    }
                }) {
                    Box(
                        modifier = modifier
                            .clip(RoundedCornerShape(40.dp))
                            .background(Color.Black)
                            .width(100.dp)
                            .height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (onResume) {
                            Icon(
                                imageVector = Icons.Default.Pause,
                                contentDescription = "",
                                tint = Green500,
                                modifier = modifier.size(40.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "",
                                tint = Green500,
                                modifier = modifier.size(40.dp)
                            )
                        }
                    }
                }
                IconButton(
                    onClick = {
                        player.stopSound()
                        onNavigateToRest()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = modifier
                            .size(40.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}