package com.vn.wecare.feature.account.view.goalhistory

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.dashboard.GoalDetailUiState
import com.vn.wecare.feature.home.goal.dashboard.goaldetail.GoalDetailSection
import com.vn.wecare.feature.home.goal.dashboard.goalrecords.GoalRecordsSection
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalDetailModalBottomSheet(
    modifier: Modifier = Modifier,
    goal: Goal,
    closeBottomSheet: () -> Unit,
    resetGoal: (goal: Goal) -> Unit,
    uiState: GoalDetailUiState,
    recordList: List<GoalWeeklyRecord>,
    isResetEnabled: Boolean
) {

    val tabRowItems = listOf("Detail", "Records")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val openAttentionDialog = remember { mutableStateOf(false) }

    if (openAttentionDialog.value) {
        ResetGoalInformationDialog(
            modifier = modifier,
            onDismissDialog = { openAttentionDialog.value = false },
            onResetGoalClick = {
                resetGoal(goal)
            },
        )
    }

    Scaffold(modifier = modifier
        .fillMaxSize()
        .padding(normalPadding), topBar = {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = MaterialTheme.colors.primary
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(modifier = modifier.background(MaterialTheme.colors.background),
                    text = {
                        Text(text = item, style = MaterialTheme.typography.button)
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.secondary
                )
            }
        }
    }, bottomBar = {
        Row(
            modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                modifier = modifier
                    .weight(1f)
                    .padding(end = smallPadding)
                    .height(40.dp),
                onClick = {
                    closeBottomSheet()
                },
                shape = RoundedCornerShape(mediumRadius)
            ) {
                Text(text = stringResource(id = R.string.close_dialog_title))
            }
            Button(
                modifier = modifier
                    .weight(1f)
                    .padding(start = smallPadding)
                    .height(40.dp),
                enabled = isResetEnabled,
                onClick = {
                    openAttentionDialog.value = true
                },
                shape = RoundedCornerShape(mediumRadius)
            ) {
                Text(text = "RESET")
            }
        }
    }) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxSize(),
            pageCount = tabRowItems.size,
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = false
        ) {
            if (it == 0) {
                GoalDetailSection(
                    modifier = modifier, detailUi = uiState
                )
            } else {
                GoalRecordsSection(modifier = modifier, records = recordList, onItemClick = {})
            }
        }
    }
}