package com.vn.wecare.feature.food.addmeal.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.BrunchDining
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.vn.wecare.R
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.common_composable.VerticalExpandableView
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
    viewModel: AddMealViewModel
) {

    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        VerticalExpandableView(header = {
            WecareAppBar(modifier = modifier,
                title = "Meals",
                onLeadingIconPress = navigateUp,
//                trailingIconRes = if (isExpanded) R.drawable.ic_search_off else R.drawable.ic_search,
//                onTrailingIconPress = {
//                    isExpanded = !isExpanded
//                }
            )
        }, expandablePart = {
            OutlinedTextField(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = normalPadding),
                value = viewModel.searchText,
                onValueChange = viewModel::onSearchTextChanged,
                label = { Text("Search for meal") },
                maxLines = 1,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                })
        }, isExpanded = isExpanded)

        TabRow(
            modifier = modifier.fillMaxWidth(),
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