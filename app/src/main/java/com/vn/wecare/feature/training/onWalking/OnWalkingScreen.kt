package com.vn.wecare.feature.training.onWalking

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import com.mapbox.navigation.core.MapboxNavigation
import com.vn.wecare.R
import com.vn.wecare.feature.training.dashboard.TopBar
import com.vn.wecare.feature.training.view_route.widget.HistoryFilesDirectory
import com.vn.wecare.feature.training.widget.ClockWidget
import com.vn.wecare.feature.training.widget.TimerWidget
import com.vn.wecare.ui.theme.*
import com.vn.wecare.feature.training.widget.CustomAlertDialog
import okio.Path.Companion.toPath
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.StandardCopyOption

enum class UserTarget {
    distance, time, calo, none
}

@Composable
fun OnWalkingScreen(
    modifier: Modifier = Modifier,
    userTarget: UserTarget,
    mapboxNavigation: MapboxNavigation,
    onNavigateToSuccess: () -> Unit
) {
    var onResume by remember { mutableStateOf(true) }
    var openDialog by remember { mutableStateOf(false) }

    mapboxNavigation.historyRecorder.startRecording()

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                Text(
                    text = stringResource(id = R.string.stop_confirm_title),
                    style = MaterialTheme.typography.h3
                )
            },
            confirmButton = {
                Button(onClick = {
                    mapboxNavigation.historyRecorder.stopRecording { filePath ->
                        if (filePath != null) {
                            val fileName =
                                "/data/data/com.vn.wecare/files/mbx_nav/history/replay-history-activity.pbf.gz"
                            try {
                                val file = File(fileName)
                                file.createNewFile()
                                Log.e("trung history", "create file SUCCESS")

                            } catch (e: Exception) {
                                Log.e("trung history", "create file FAIL")
                            }
                            try {
                                File(filePath).copyTo(File(fileName), overwrite = true)
                                Log.e("trung copy", "copy file SUCCESS")
                            } catch (e: Exception) {
                                Log.e("trung copy", "copy file FAIL")
                            }
                        }
                    }
                    onNavigateToSuccess()
                }) {
                    Text(stringResource(id = R.string.button_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text(stringResource(id = R.string.button_cancel))
                }
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(12.dp)
            ),
        content = {
            Column(
                modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //ClockWidget(modifier = modifier.padding(64.dp))
                when (userTarget) {
                    UserTarget.distance -> TargetDistance(modifier = modifier, onResume = onResume)
                    UserTarget.calo -> TargetCalorie(modifier = modifier, onResume = onResume)
                    else -> TargetTime(modifier = modifier, onResume = onResume)
                }
                Spacer(
                    modifier = modifier
                        .height(16.dp)
                )
                StopAndPause(
                    onStop = {
                        openDialog = !openDialog
                    },
                    onPause = { onResume = !onResume })
            }
        }
    )
}

@Composable
fun TargetDistance(
    modifier: Modifier,
    onResume: Boolean
) {
    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Distance", modifier = modifier.padding(4.dp))
        Text(
            text = "00.00.00",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(4.dp)
        )
        Box(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(Grey500)
        )
        Row(
            modifier = modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Time", modifier = modifier.padding(4.dp))
                TimerWidget(
                    modifier = modifier.padding(8.dp),
                    onResume = onResume
                )
            }
            Box(
                modifier = modifier
                    .padding(vertical = 16.dp)
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Grey500)
            )
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Calories", modifier = modifier.padding(4.dp))
                Text(
                    text = "00.00.00",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun TargetTime(
    modifier: Modifier,
    onResume: Boolean
) {
    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Time", modifier = modifier.padding(4.dp))
        TimerWidget(
            modifier = modifier.padding(8.dp),
            onResume = onResume
        )
        Box(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(Grey500)
        )
        Row(
            modifier = modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Distance", modifier = modifier.padding(4.dp))
                Text(
                    text = "00.00.00",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(4.dp)
                )
            }
            Box(
                modifier = modifier
                    .padding(vertical = 16.dp)
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Grey500)
            )
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Calories", modifier = modifier.padding(4.dp))
                Text(
                    text = "00.00.00",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun TargetCalorie(
    modifier: Modifier,
    onResume: Boolean
) {
    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Calories", modifier = modifier.padding(4.dp))
        Text(
            text = "00.00.00",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(8.dp)
        )
        Box(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(Grey500)
        )
        Row(
            modifier = modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Distance", modifier = modifier.padding(4.dp))
                Text(
                    text = "00.00.00",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(4.dp)
                )
            }
            Box(
                modifier = modifier
                    .padding(vertical = 16.dp)
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Grey500)
            )
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Time", modifier = modifier.padding(4.dp))
                TimerWidget(
                    modifier = modifier.padding(8.dp),
                    onResume = onResume
                )
            }
        }
    }
}

@Composable
fun StopAndPause(
    modifier: Modifier = Modifier,
    onStop: () -> Unit,
    onPause: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Button(
            modifier = modifier
                .weight(1f),
            onClick = {
                onStop()
            },
            shape = RoundedCornerShape(mediumRadius),
            colors = ButtonDefaults.buttonColors(backgroundColor = Grey500)
        ) {
            Text(text = stringResource(id = R.string.button_stop), color = Green500)
        }
        Button(
            modifier = modifier
                .weight(2f),
            onClick = {
                onPause()
            },
            shape = RoundedCornerShape(mediumRadius)
        ) {
            Text(text = stringResource(id = R.string.button_pause))
        }
    }
}

@Composable
fun ExitOrView(
    modifier: Modifier = Modifier,
    onExit: () -> Unit,
    onView: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Button(
            modifier = modifier
                .weight(1f),
            onClick = {
                onExit()
            },
            shape = RoundedCornerShape(mediumRadius),
            colors = ButtonDefaults.buttonColors(backgroundColor = Grey500)
        ) {
            Text(text = stringResource(id = R.string.button_exit), color = Green500)
        }
        Button(
            modifier = modifier
                .weight(2f),
            onClick = {
                onView()
            },
            shape = RoundedCornerShape(mediumRadius)
        ) {
            Text(text = stringResource(id = R.string.button_view))
        }
    }
}