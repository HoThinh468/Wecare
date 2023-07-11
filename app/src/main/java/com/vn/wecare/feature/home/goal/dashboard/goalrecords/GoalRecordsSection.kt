package com.vn.wecare.feature.home.goal.dashboard.goalrecords

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord

@Composable
fun GoalRecordsSection(
    modifier: Modifier,
    records: List<GoalWeeklyRecord>,
    onItemClick: (record: GoalWeeklyRecord) -> Unit
) {
    LazyColumn {
        items(records.size) { i ->
            GoalWeeklyRecordItem(
                modifier = modifier,
                index = i + 1,
                record = records[i],
                onItemClick = onItemClick,
            )
        }
    }
}