package com.vn.wecare.feature.home.goal.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.common_composable.WecareAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalDashboardAppBar(
    modifier: Modifier,
    navigateBack: () -> Unit,
    moveToGoalHistoryScreen: () -> Unit,
    tabRowItems: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    dashboardUI: GoalDashboardAppbarUiState
) {
    Column(modifier = modifier.fillMaxWidth()) {
        WecareAppBar(
            modifier = modifier,
            onLeadingIconPress = navigateBack,
            title = "Goal dashboard",
            trailingIconRes = R.drawable.ic_timeline,
            onTrailingIconPress = moveToGoalHistoryScreen
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = normalPadding)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = dashboardUI.goalName,
                        style = MaterialTheme.typography.h1.copy(MaterialTheme.colors.primary)
                    )
                    Spacer(modifier = modifier.height(halfMidPadding))
                    Row(
                        modifier = modifier
                            .width(IntrinsicSize.Max)
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "From:", style = MaterialTheme.typography.caption.copy(
                                    color = colorResource(
                                        id = R.color.Black450
                                    )
                                )
                            )
                            Text(
                                dashboardUI.startDate, style = MaterialTheme.typography.body1
                            )
                        }
                        Divider(
                            modifier = modifier
                                .fillMaxHeight()
                                .padding(horizontal = midPadding)
                                .width(1.dp)
                        )
                        Column {
                            Text(
                                "To:", style = MaterialTheme.typography.caption.copy(
                                    color = colorResource(
                                        id = R.color.Black450
                                    )
                                )
                            )
                            Text(
                                dashboardUI.endDate, style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
                Image(
                    modifier = modifier.size(90.dp), painter = painterResource(
                        id = dashboardUI.imgSrc
                    ), contentDescription = null
                )
            }
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                contentColor = MaterialTheme.colors.primary
            ) {
                tabRowItems.forEachIndexed { index, item ->
                    Tab(
                        modifier = modifier.background(MaterialTheme.colors.background),
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
        }
    }
}