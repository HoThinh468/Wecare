package com.vn.wecare.feature.food.mealplan.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.food.addmeal.ui.NutrientIndexItem
import com.vn.wecare.feature.food.mealdetail.NutrientSubInfoItem
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.WecareAppBar

@Composable
fun DailyMealPlanAppBar(
    modifier: Modifier,
    navigateUp: () -> Unit,
    totalCalories: Int,
    totalProtein: Int,
    totalFat: Int,
    totalCarbs: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.onPrimary)
    ) {
        WecareAppBar(
            modifier = modifier,
            title = "31st July, 2023",
            onLeadingIconPress = navigateUp,
        )
        Column(modifier = modifier.padding(normalPadding)) {
            Text(text = "Nutrition detail", style = MaterialTheme.typography.body1)
            NutrientSubInfoItem(
                modifier = modifier,
                icon = Icons.Default.LocalFireDepartment,
                color = Red400,
                index = "$totalCalories cal"
            )
            Spacer(modifier = modifier.height(smallPadding))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()
            ) {
                NutrientIndexItem(
                    modifier = modifier,
                    color = Red400,
                    title = "Protein",
                    index = "${totalProtein}g"
                )
                NutrientIndexItem(
                    modifier = modifier, color = Yellow, title = "Fat", index = "${totalFat}g"
                )
                NutrientIndexItem(
                    modifier = modifier, color = Blue, title = "Carbs", index = "${totalCarbs}g"
                )
            }
        }
        Divider()
    }
}