package com.vn.wecare.feature.food.addmeal.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.BrunchDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.R
import com.vn.wecare.utils.common_composable.WecareAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddMealAppBar(
    modifier: Modifier,
    pagerState: PagerState,
    tabRowItems: List<String>,
    navigateUp: () -> Unit,
    coroutineScope: CoroutineScope,
    moveToSearchMealScreen: () -> Unit
) {

    Column(modifier = modifier.fillMaxWidth()) {
        WecareAppBar(
            modifier = modifier,
            title = "Meals",
            onLeadingIconPress = navigateUp,
            trailingIconRes = R.drawable.ic_search,
            onTrailingIconPress = {
                moveToSearchMealScreen()
            },
        )

        TabRow(
            modifier = modifier.fillMaxWidth(),
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
                    icon = {
                        Icon(
                            imageVector = when (index) {
                                0 -> Icons.Default.BreakfastDining
                                1 -> Icons.Default.LunchDining
                                2 -> Icons.Default.BrunchDining
                                else -> Icons.Default.DinnerDining
                            }, contentDescription = null
                        )
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.secondary
                )
            }
        }
    }
}