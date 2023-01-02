package com.vn.wecare.feature.training.widget

import android.opengl.Visibility
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.maps.extension.style.expressions.dsl.generated.distance
import com.vn.wecare.R
import com.vn.wecare.feature.training.onWalking.UserTarget
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun TargetChosen(
    modifier: Modifier = Modifier,
    goScreen: (UserTarget, TargetIndex) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.TopCenter,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var selectedIndex by remember { mutableStateOf(0) }
                var target by remember { mutableStateOf(TargetIndex(0)) }
                Column(
                    modifier
                        .wrapContentHeight()
                        .padding(bottom = 22.dp)
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    val items =
                        listOf("Distance Target", "Time Target", "Calorie Target", "No Target")
                    Text(
                        modifier = modifier
                            .padding(top = 16.dp)
                            .clickable(onClick = { expanded = true }),
                        text = items[selectedIndex],
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.primary
                    )
                    DropdownMenu(
                        modifier = modifier.background(Color.White),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(onClick = {
                                selectedIndex = index
                                expanded = false
                            }) {
                                Text(s)
                            }
                        }
                    }
                }
                when (selectedIndex) {
                    0 -> {
                        target = distanceTarget(modifier = modifier)
                    }
                    1 -> {
                        target = timeTarget(modifier = modifier)
                    }
                    2 -> {
                        target = calorieTarget(modifier = modifier)
                    }
                    else -> NoTarget(modifier = modifier)
                }
                Button(
                    modifier = modifier
                        .width(300.dp)
                        .padding(top = normalPadding)
                        .height(50.dp),
                    onClick = {
                        goScreen(
                            when (selectedIndex) {
                                0 -> UserTarget.distance
                                1 -> UserTarget.time
                                2 -> UserTarget.calo
                                else -> UserTarget.none
                            },
                            target
                        )
                    },
                    shape = RoundedCornerShape(mediumRadius)
                ) {
                    Text(text = stringResource(id = R.string.button_go))
                }
            }
        }
    )
}

@Composable
fun distanceTarget(
    modifier: Modifier
): TargetIndex {
    var first by remember { mutableStateOf(0) }
    var second by remember { mutableStateOf(0) }
    Row(
        modifier.height(124.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        first = numberPickerSpinner(modifier = modifier, max = 999, min = 0)
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            text = ".",
            fontSize = 32.sp,
            color = MaterialTheme.colors.primary
        )
        second = numberPickerSpinner(modifier = modifier, max = 99, min = 0)
        Spacer(modifier = modifier.width(32.dp))
        Text(text = "km", fontSize = 28.sp, color = MaterialTheme.colors.primary)
    }
    return TargetIndex(first, second)
}

@Composable
fun timeTarget(
    modifier: Modifier
): TargetIndex {
    var hour by remember { mutableStateOf(0) }
    var min by remember { mutableStateOf(0) }
    var sec by remember { mutableStateOf(0) }
    Row(
        modifier.height(124.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        hour = numberPickerSpinner(modifier = modifier, max = 12, min = 0)
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            text = ":",
            fontSize = 32.sp,
            color = MaterialTheme.colors.primary
        )
        min = numberPickerSpinner(modifier = modifier, max = 59, min = 0)
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            text = ":",
            fontSize = 32.sp,
            color = MaterialTheme.colors.primary
        )
        sec = numberPickerSpinner(modifier = modifier, max = 50, min = 0)
        Spacer(modifier = modifier.width(32.dp))
        Text(text = "hour", fontSize = 28.sp, color = MaterialTheme.colors.primary)
    }
    return TargetIndex(hour, min, sec)
}

@Composable
fun calorieTarget(
    modifier: Modifier
): TargetIndex {
    var time by remember { mutableStateOf(0) }
    Row(
        modifier.height(124.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        time = numberPickerSpinner(modifier = modifier, max = 9999, min = 50)
        Spacer(modifier = modifier.width(32.dp))
        Text(text = "Cal", fontSize = 28.sp, color = MaterialTheme.colors.primary)
    }
    return TargetIndex(time)
}

@Composable
fun NoTarget(
    modifier: Modifier
) {
    Text(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp),
        text = "Start your training without any target.",
        fontSize = 16.sp,
        color = MaterialTheme.colors.primary
    )
}

data class TargetIndex(
    val first: Int,
    val second: Int? = null,
    val third: Int? = null
)


