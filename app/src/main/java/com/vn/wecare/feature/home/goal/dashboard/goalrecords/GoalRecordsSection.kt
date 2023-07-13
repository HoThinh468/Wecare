package com.vn.wecare.feature.home.goal.dashboard.goalrecords

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.xxxExtraPadding

@Composable
fun GoalRecordsSection(
    modifier: Modifier,
    records: List<GoalWeeklyRecord>,
    onItemClick: (record: GoalWeeklyRecord) -> Unit
) {
    LazyColumn(modifier = modifier.padding(top = normalPadding, bottom = xxxExtraPadding)) {
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