package com.vn.wecare.feature.training.walking.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TargetChosen(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(8.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.TopCenter,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var selectedIndex by remember { mutableStateOf(0) }
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
                        color = Color.White
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
                    0 -> DistanceTarget(modifier = modifier)
                    1 -> TimeTarget(modifier = modifier)
                    2 -> CalorieTarget(modifier = modifier)
                    else -> NoTarget(modifier = modifier)
            }
            }
        }
    )
}

@Composable
fun DistanceTarget(
    modifier: Modifier
) {
    Row(
        modifier.height(124.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        NumberPickerSpinner(modifier = modifier, max = 999, min = 0)
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            text = ".",
            fontSize = 32.sp
        )
        NumberPickerSpinner(modifier = modifier, max = 99, min = 0)
        Spacer(modifier = modifier.width(32.dp))
        Text(text = "km", fontSize = 28.sp)
    }
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
        NumberPickerSpinner(modifier = modifier, max = 12, min = 0)
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            text = ":",
            fontSize = 32.sp
        )
        NumberPickerSpinner(modifier = modifier, max = 59, min = 0)
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            text = ":",
            fontSize = 32.sp
        )
        NumberPickerSpinner(modifier = modifier, max = 50, min = 0)
        Spacer(modifier = modifier.width(32.dp))
        Text(text = "hour", fontSize = 28.sp)
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
        NumberPickerSpinner(modifier = modifier, max = 9999, min = 50)
        Spacer(modifier = modifier.width(32.dp))
        Text(text = "Cal", fontSize = 28.sp)
    }
}

@Composable
fun NoTarget(
    modifier: Modifier
) {
    Text(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp),
        text = "Start your training without any target.",
        fontSize = 16.sp
    )
}


