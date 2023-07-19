package com.vn.wecare.feature.home.goal.weeklyrecords

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.WecareAppBar
import com.vn.wecare.utils.getProgressInFloatWithIntInput

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalWeeklyRecordDetailScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: WeeklyRecordViewModel,
    record: GoalWeeklyRecord
) {

    val uiState = viewModel.uiState.collectAsState().value

    uiState.getRecordsResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(LocalContext.current, "Load data successfully!", Toast.LENGTH_SHORT)
                    .show()
            }

            is Response.Error -> {
                Toast.makeText(LocalContext.current, it.e?.message, Toast.LENGTH_SHORT).show()
            }

            else -> { /* do nothing */
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        topBar = {
            WecareAppBar(
                modifier = modifier, onLeadingIconPress = navigateBack, title = "Weekly report"
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(halfMidPadding)
                .verticalScroll(rememberScrollState())
        ) {
            OverviewSection(
                modifier = modifier,
                totalRecords = record.numberOfDayRecord,
                progress = uiState.progress,
                goalStatus = GoalStatus.getGoalStatusFromValue(record.status),
                dateSetGoal = record.startDate,
                dateEndGoal = record.endDate
            )
            Spacer(modifier = modifier.height(normalPadding))
            WeeklyGoalBarChartReportSection(
                modifier = modifier,
                dailyRecordList = uiState.records,
                record = record,
                totalCaloriesIn = uiState.totalCaloriesIn,
                totalCaloriesOut = uiState.totalCaloriesOut,
                caloriesInProgress = getProgressInFloatWithIntInput(
                    uiState.totalCaloriesIn, record.weeklyCaloriesGoal
                ),
                caloriesOutProgress = getProgressInFloatWithIntInput(
                    uiState.totalCaloriesOut, record.weeklyCaloriesOutGoal
                )
            )
            Spacer(modifier = modifier.height(normalPadding))
            WeeklyRecordDetailIndex(
                modifier = modifier,
                record = record,
                averageCaloriesIn = uiState.averageCaloriesInEachDay,
                averageCaloriesOut = uiState.averageCaloriesOutEachDay
            )
            Spacer(modifier = modifier.height(midPadding))
        }
    }
}