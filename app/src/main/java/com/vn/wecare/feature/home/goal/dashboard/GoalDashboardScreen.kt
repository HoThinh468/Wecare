package com.vn.wecare.feature.home.goal.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.home.goal.dashboard.goaldetail.GoalDetailSection
import com.vn.wecare.feature.home.goal.dashboard.goalrecords.GoalRecordsSection
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalDashboardScreen(
    modifier: Modifier = Modifier, navigateBack: () -> Unit, viewModel: GoalDashboardViewModel
) {

    val tabRowItems = listOf("Detail", "Records")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val dashboardUi = viewModel.appbarUi.collectAsState().value

    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            GoalDashboardAppBar(
                modifier = modifier,
                navigateBack = navigateBack,
                moveToGoalHistoryScreen = {},
                tabRowItems = tabRowItems,
                pagerState = pagerState,
                coroutineScope = coroutineScope,
                dashboardUI = dashboardUi
            )
        },
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = midPadding, horizontal = normalPadding),
            pageCount = tabRowItems.size,
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = false
        ) {
            if (it == 0) {
                GoalDetailSection(
                    modifier = modifier, goal = LatestGoalSingletonObject.getInStance()
                )
            } else {
                GoalRecordsSection(modifier = modifier, records = emptyList())
            }
        }
    }
}