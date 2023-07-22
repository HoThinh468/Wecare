package com.vn.wecare.feature.home.step_count.ui.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.tinyPadding
import com.vn.wecare.utils.getProgressInFloatWithIntInput

@Preview
@Composable
fun test() {
    DailyCalories(
        caloriesIn = 200,
        caloriesInProgress = 0.2f,
        caloriesOut = 100,
        caloriesOutProgress = 0.5f
    )
}

@Composable
fun DailyCalories(
    remainingCalo: Int = 0,
    modifier: Modifier = Modifier,
    caloriesIn: Int,
    caloriesInProgress: Float,
    caloriesOut: Int,
    caloriesOutProgress: Float,
    caloriesOutTarget: Float = 0f,
) {
    Card(
        modifier = modifier.fillMaxSize(),
        shape = Shapes.small,
        elevation = 12.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(normalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CaloriesCategoryItem(
                modifier = modifier,
                calories = caloriesIn,
                category = "Calories in",
                icon = Icons.Default.ArrowDropUp,
                color = MaterialTheme.colors.primary,
                progress = caloriesInProgress,
                isCaloOut = false
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

@Composable
fun CaloriesCategoryItem(
    modifier: Modifier = Modifier,
    calories: Int,
    category: String,
    icon: ImageVector,
    color: Color,
    progress: Float,
    isCaloOut: Boolean = true
) {
    Row(
        modifier = modifier.padding(vertical = tinyPadding),
    ) {
        VerticalLinearProgressIndicator(
            color = color,
            value = progress,
        )
        Column(
            modifier = modifier.padding(start = tinyPadding),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (isCaloOut)
                    Icon(
                        modifier = modifier
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_fire),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                else
                    Icon(
                        modifier = modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_rice),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                Text(
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(horizontal = smallPadding),
                    text = "$calories cal",
                    style = MaterialTheme.typography.body1
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = color)
                Text(
                    text = category,
                    style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
                )
            }

        }
    }
}