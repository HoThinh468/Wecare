package com.vn.wecare.feature.food.common

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.OpenSans
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.getProgressInFloatWithIntInput
import java.util.Calendar

@Composable
fun MealTypeOverviewNutrition(
    modifier: Modifier,
    onDateChangeListener: (dayOfMonth: Int, month: Int, year: Int) -> Unit,
    mealUiState: MealUiState
) {
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onDateChangeListener(mDayOfMonth, mMonth, mYear)
        }, year, month, day
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        shape = Shapes.medium,
        elevation = smallElevation
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = normalPadding, horizontal = midPadding),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = mealUiState.dateTime, style = MaterialTheme.typography.h4)
                IconButton(modifier = modifier.size(32.dp), onClick = { datePickerDialog.show() }) {
                    Icon(
                        imageVector = Icons.Default.EditCalendar,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Text(modifier = modifier
                .fillMaxWidth()
                .padding(top = smallPadding),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            fontFamily = OpenSans
                        )
                    ) {
                        append(mealUiState.calories.toString())
                    }
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.Black450),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            fontFamily = OpenSans
                        )
                    ) {
                        append("/${mealUiState.targetCalories} kcal")
                    }
                })
            LinearProgressIndicator(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = halfMidPadding, bottom = halfMidPadding)
                    .height(6.dp),
                strokeCap = StrokeCap.Round,
                progress = getProgressInFloatWithIntInput(
                    mealUiState.calories, mealUiState.targetCalories
                ),
                backgroundColor = MaterialTheme.colors.secondary.copy(0.4f)
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NutrientIndexItem(
                    modifier = modifier,
                    color = Red400,
                    title = "Protein",
                    currentIndex = mealUiState.protein,
                    targetIndex = mealUiState.targetProtein
                )
                NutrientIndexItem(
                    modifier = modifier,
                    color = Yellow,
                    title = "Fat",
                    currentIndex = mealUiState.fat,
                    targetIndex = mealUiState.targetFat
                )
                NutrientIndexItem(
                    modifier = modifier,
                    color = Blue,
                    title = "Carbs",
                    currentIndex = mealUiState.carbs,
                    targetIndex = mealUiState.targetCarbs
                )
            }
        }
    }
}