package com.vn.wecare.feature.home.dashboard.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.OpenSans
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding

@Composable
fun DailyCalories(
    modifier: Modifier,
    remainedCalories: Int,
    caloriesIn: Int,
    caloriesInProgress: Float,
    caloriesOut: Int,
    caloriesOutProgress: Float,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Text(
                "Daily calories", style = MaterialTheme.typography.h5
            )
            Text(
                "Calories remained",
                style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
            )
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold, fontSize = 32.sp, fontFamily = OpenSans
                    )
                ) {
                    append(remainedCalories.toString())
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = OpenSans
                    )
                ) {
                    append(" cal")
                }
            })
            Spacer(modifier = modifier.height(halfMidPadding))
            Divider()
            Spacer(modifier = modifier.height(halfMidPadding))
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CaloriesCategoryItem(
                    modifier = modifier,
                    calories = caloriesIn,
                    category = "Calories in",
                    icon = Icons.Default.ArrowDropUp,
                    color = MaterialTheme.colors.primary,
                    progress = caloriesInProgress
                )
                CaloriesCategoryItem(
                    modifier = modifier,
                    calories = caloriesOut,
                    category = "Calories out",
                    icon = Icons.Default.ArrowDropDown,
                    color = Red400,
                    progress = caloriesOutProgress
                )
            }
        }
    }
}

@Composable
fun CaloriesCategoryItem(
    modifier: Modifier,
    calories: Int,
    category: String,
    icon: ImageVector,
    color: Color,
    progress: Float
) {
    Column {
        Text(text = "$calories cal", style = MaterialTheme.typography.body1)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = color)
            Text(
                text = category,
                style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
            )
        }
        LinearProgressIndicator(
            modifier = modifier
                .width(88.dp)
                .height(4.dp),
            color = color,
            strokeCap = StrokeCap.Round,
            progress = progress
        )
    }
}