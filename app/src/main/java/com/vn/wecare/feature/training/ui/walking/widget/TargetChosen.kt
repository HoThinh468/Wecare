package com.vn.wecare.feature.training.ui.walking.widget

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
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun TargetChosen(
    modifier: Modifier = Modifier,
    goScreen: () -> Unit
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
                var distance by remember { mutableStateOf(0) }
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
                        distance = distanceTarget(modifier = modifier)
                }
                    1 -> TimeTarget(modifier = modifier)
                    2 -> CalorieTarget(modifier = modifier)
                    else -> NoTarget(modifier = modifier)
                }
                Button(
                    modifier = modifier
                        .width(300.dp)
                        .padding(top = normalPadding)
                        .height(50.dp),
                    onClick = {
                        goScreen()
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
): Int {
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
    return first
}

@Composable
fun TimeTarget(
    modifier: Modifier
) {
    Row(
        modifier.height(124.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        numberPickerSpinner(modifier = modifier, max = 12, min = 0)
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            text = ":",
            fontSize = 32.sp,
            color = MaterialTheme.colors.primary
        )
        numberPickerSpinner(modifier = modifier, max = 59, min = 0)
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            text = ":",
            fontSize = 32.sp,
            color = MaterialTheme.colors.primary
        )
        numberPickerSpinner(modifier = modifier, max = 50, min = 0)
        Spacer(modifier = modifier.width(32.dp))
        Text(text = "hour", fontSize = 28.sp, color = MaterialTheme.colors.primary)
    }
}

@Composable
fun CalorieTarget(
    modifier: Modifier
) {
    Row(
        modifier.height(124.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        numberPickerSpinner(modifier = modifier, max = 9999, min = 50)
        Spacer(modifier = modifier.width(32.dp))
        Text(text = "Cal", fontSize = 28.sp, color = MaterialTheme.colors.primary)
    }
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

interface ChosenType {}

class Distance(
    var first: Int = 0,
    var second: Int = 0
) : ChosenType {
}


