package com.vn.wecare.feature.home.goal.dashboard.goalrecords

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord

@Composable
fun GoalRecordsSection(
    modifier: Modifier, records: List<GoalWeeklyRecord>
) {
    LazyColumn {
        items(2) { i ->
            GoalWeeklyRecordItem(modifier = modifier) {
            }
        }
    }
}