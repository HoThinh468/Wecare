package com.vn.wecare.feature.home.goal.dashboard

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.dashboard.goaldetail.GoalDetailSection
import com.vn.wecare.feature.home.goal.dashboard.goalrecords.GoalRecordsSection
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.common_composable.LoadingDialog

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalDashboardScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: GoalDashboardViewModel,
    navigateWeeklyRecordScreen: (record: GoalWeeklyRecord) -> Unit
) {

    val tabRowItems = listOf("Detail", "Records")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val dashboardUi = viewModel.appbarUi.collectAsState().value
    val detailUi = viewModel.detailUi.collectAsState().value
    val recordUi = viewModel.recordUi.collectAsState().value

    recordUi.getRecordsResponse.let {
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
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            GoalDashboardAppBar(
                modifier = modifier,
                navigateBack = navigateBack,
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
                    modifier = modifier, detailUi = detailUi
                )
            } else {
                GoalRecordsSection(
                    modifier = modifier,
                    records = recordUi.records,
                    onItemClick = navigateWeeklyRecordScreen
                )
            }
        }
    }
}