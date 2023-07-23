package com.vn.wecare.feature.food.mealplan.weekly

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMediumIconSize
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.largeIconSize
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeeklyMealPlanItem(
    modifier: Modifier,
    day: String,
    dayOfWeek: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true
) {

    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = normalPadding),
        shape = Shapes.small,
        backgroundColor = MaterialTheme.colors.background,
        onClick = {
            if (isEnabled) {
                onClick()
            }
            else {
                Toast.makeText(
                    context, "Meal plan for this day is not available yet!", Toast.LENGTH_SHORT
                ).show()
            }
        },
        border = BorderStroke(
            width = 1.dp,
            color = if (isEnabled) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Row(
                modifier = modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = modifier
                        .size(largeIconSize)
                        .clip(CircleShape)
                        .background(if (isEnabled) MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dayOfWeek.uppercase(), style = MaterialTheme.typography.button.copy(
                            if (isEnabled) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary
                        )
                    )
                }
                Spacer(modifier = modifier.width(halfMidPadding))
                Column {
                    Text(text = day, style = MaterialTheme.typography.body1)
                    Spacer(modifier = modifier.height(smallPadding))
                    Text(
                        text = "Target calories: ${WecareCaloriesObject.getInstance().caloriesInEachDay} cal",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ArrowForwardIos, modifier = modifier
                    .size(
                        halfMediumIconSize
                    )
                    .align(Alignment.CenterEnd), contentDescription = null
            )
        }
    }
}