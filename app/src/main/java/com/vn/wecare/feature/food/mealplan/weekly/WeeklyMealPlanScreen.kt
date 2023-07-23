package com.vn.wecare.feature.food.mealplan.weekly

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.getDayFormatWithYear
import com.vn.wecare.utils.getDayOfWeekPrefix
import java.time.LocalDate

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeeklyMealPlanScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    moveToDailyMealPlanScreen: (dayOfMonth: Int, month: Int, year: Int) -> Unit,
    viewModel: WeeklyMealPlanViewModel
) {

    val uiState = viewModel.uiState.collectAsState().value
    val listOfLocalDate = viewModel.localDateList.collectAsState().value

    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        topBar = {
            WeeklyMealPlanAppBar(modifier = modifier,
                navigateUp = {
                    navigateUp()
                },
                firstDayOfWeeK = uiState.firstDayOfWeek,
                lastDayOfWeek = uiState.lastDayOfWeek,
                onPreviousWeekClick = { viewModel.onPreviousWeekClick() },
                onNextWeekClick = { viewModel.onNextWeekClick() },
                isNextWeekClickEnable = uiState.isNextClickEnable
            )
        },
    ) {
        LazyColumn(modifier = modifier.padding(normalPadding)) {
            items(listOfLocalDate.size) {
                val localDate = listOfLocalDate[it]
                WeeklyMealPlanItem(
                    modifier = modifier,
                    day = getDayFormatWithYear(localDate),
                    dayOfWeek = getDayOfWeekPrefix(localDate.dayOfWeek.value + 1),
                    onClick = {
                        moveToDailyMealPlanScreen(
                            localDate.dayOfMonth, localDate.monthValue, localDate.year
                        )
                    },
                    isEnabled = LocalDate.now() >= localDate
                )
            }
        }
    }
}