package com.vn.wecare.feature.training.onWalking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.OnLifecycleEvent
import com.mapbox.navigation.core.MapboxNavigation
import com.vn.wecare.R
import com.vn.wecare.feature.training.dashboard.history.model.TrainingHistory
import com.vn.wecare.feature.training.utils.UserAction
import com.vn.wecare.feature.training.utils.convertUserActionToString
import com.vn.wecare.feature.training.utils.stringWith2Decimals
import com.vn.wecare.feature.training.widget.timerWidget
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.mediumRadius
import java.io.File

enum class UserTarget {
    distance, time, calo, none
}

@Composable
fun OnWalkingScreen(
    modifier: Modifier = Modifier,
    userTarget: UserTarget,
    mapboxNavigation: MapboxNavigation,
    onNavigateToSuccess: () -> Unit,
    viewModel : OnWalkingViewModel,
    userAction: UserAction
) {
    var onResume by remember { mutableStateOf(true) }
    var openDialog by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf(0) }
    var distance by remember { mutableStateOf(viewModel.distance) }
    var kcal by remember { mutableStateOf(viewModel.kcal) }

    LifecycleEventObserver{_, event ->
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                onResume = true
                openDialog = false
                duration = 0
                distance = mutableStateOf(0.0)
                kcal = mutableStateOf(0.0)
            }
            Lifecycle.Event.ON_STOP -> {
                onResume = true
                openDialog = false
                duration = 0
                distance = mutableStateOf(0.0)
                kcal = mutableStateOf(0.0)
            }
            else -> {}
        }
    }

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
                    viewModel.addTrainingHistory(
                        TrainingHistory(
                            convertUserActionToString(userAction),
                            System.currentTimeMillis(),
                            duration,
                            stringWith2Decimals(kcal.value).toDouble(),
                            stringWith2Decimals(distance.value).toDouble()
                        )
                    )
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
                duration = when (userTarget) {
                    UserTarget.distance -> targetDistance(modifier = modifier, onResume = onResume, distance.value, kcal.value)
                    UserTarget.calo -> targetCalorie(modifier = modifier, onResume = onResume, distance.value, kcal.value)
                    else -> targetTime(modifier = modifier, onResume = onResume, distance.value, kcal.value)
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
fun targetDistance(
    modifier: Modifier,
    onResume: Boolean,
    distance: Double,
    kcal: Double
): Int {
    var duration by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Distance", modifier = modifier.padding(4.dp))
        Text(
            text = stringWith2Decimals(distance) + " Km",
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
                duration = timerWidget(
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
                    text = stringWith2Decimals(kcal),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(8.dp)
                )
            }
        }
    }
    return duration
}

@Composable
fun targetTime(
    modifier: Modifier,
    onResume: Boolean,
    distance: Double,
    kcal: Double
) : Int {
    var duration by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Time", modifier = modifier.padding(4.dp))
        duration = timerWidget(
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
                    text = stringWith2Decimals(distance),
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
                    text = stringWith2Decimals(kcal),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(8.dp)
                )
            }
        }
    }
    return duration
}

@Composable
fun targetCalorie(
    modifier: Modifier,
    onResume: Boolean,
    distance: Double,
    kcal: Double
) : Int {
    var duration by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Calories", modifier = modifier.padding(4.dp))
        Text(
            text = stringWith2Decimals(kcal),
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
                    text = stringWith2Decimals(distance),
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
                duration = timerWidget(
                    modifier = modifier.padding(8.dp),
                    onResume = onResume
                )
            }
        }
    }
    return duration
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