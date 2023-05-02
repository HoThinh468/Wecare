package com.vn.wecare.feature.food

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidRadius
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DailyNutritionScreen(
    modifier: Modifier = Modifier,
) {

    val focusManager = LocalFocusManager.current

    Scaffold(modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = { NutritionAppbar(modifier = modifier) }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = midPadding)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }) {
            NutritionOverview(modifier = modifier, progress = 0.6f)
        }
    }
}

@Composable
private fun NutritionAppbar(modifier: Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(midPadding)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Today", style = MaterialTheme.typography.body2
                )
                Text(
                    text = "Wed, 1 May", style = MaterialTheme.typography.h3
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
        OutlinedTextField(modifier = modifier
            .fillMaxWidth()
            .padding(top = normalPadding),
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Search food or recipe")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            })
    }
}

@Composable
private fun NutritionOverview(
    modifier: Modifier, progress: Float
) {

    val progressAnimationValue by animateFloatAsState(
        targetValue = progress, animationSpec = tween(1000)
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        shape = Shapes.medium,
        elevation = smallElevation
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = modifier.weight(1.2f), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = modifier.size(175.dp),
                    progress = 1f,
                    color = MaterialTheme.colors.secondary.copy(0.4f),
                    strokeWidth = 10.dp,
                )
                CircularProgressIndicator(
                    modifier = modifier.size(175.dp),
                    progress = progressAnimationValue,
                    color = MaterialTheme.colors.primary,
                    strokeWidth = 10.dp,
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "1000", style = MaterialTheme.typography.h1)
                    Text(text = "Goal: 1650 kcal", style = MaterialTheme.typography.body2)
                }
            }
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(start = mediumPadding),
                horizontalAlignment = Alignment.Start
            ) {
                NutritionOverviewItem(
                    modifier = modifier, title = "Protein", index = 22, target = 29, color = Red400
                )
                Spacer(modifier = modifier.height(smallPadding))
                NutritionOverviewItem(
                    modifier = modifier, title = "Fat", index = 40, target = 42, color = Yellow
                )
                Spacer(modifier = modifier.height(smallPadding))
                NutritionOverviewItem(
                    modifier = modifier, title = "Carbs", index = 32, target = 100, color = Blue
                )
            }
        }
    }
}

@Composable
private fun NutritionOverviewItem(
    modifier: Modifier, title: String, index: Int, target: Int, color: Color
) {

    val animatedProgress = animateFloatAsState(
        targetValue = index.toFloat() / target.toFloat(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(horizontalAlignment = Alignment.Start) {
        Text(text = title, style = MaterialTheme.typography.body1)
        Spacer(modifier = modifier.height(4.dp))
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(6.dp),
            backgroundColor = MaterialTheme.colors.secondary.copy(0.4f),
            color = color,
            progress = animatedProgress,
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = modifier.height(4.dp))
        Text(text = "${index}/$target g", style = MaterialTheme.typography.caption)
    }
}

@Composable
private fun AddYourMealItem() {

}