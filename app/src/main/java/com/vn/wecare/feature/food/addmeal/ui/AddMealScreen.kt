package com.vn.wecare.feature.food.addmeal.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.BrunchDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.data.MealsByNutrients
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.WecareAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddMealScreen(
    modifier: Modifier = Modifier, navigateUp: () -> Unit, addMealViewModel: AddMealViewModel
) {

    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState()

    val tabRowItems = listOf("Breakfast", "Lunch", "Snack", "Dinner")
    val coroutineScope = rememberCoroutineScope()

    val uiState = addMealViewModel.uiState.collectAsState()

    uiState.value.getMealsDataResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                addMealViewModel.resetGetDataResponse()
                Toast.makeText(LocalContext.current, "Load data successfully!", Toast.LENGTH_SHORT)
                    .show()
            }

            else -> { /* do nothing */
            }
        }
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            AddMealAppBar(
                modifier = modifier,
                pagerState = pagerState,
                tabRowItems = tabRowItems,
                coroutineScope = coroutineScope,
                navigateUp = navigateUp
            )
        }) {
        Column(modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }) {
            HorizontalPager(
                state = pagerState,
                modifier = modifier.fillMaxWidth(),
                pageCount = tabRowItems.size,
                verticalAlignment = Alignment.CenterVertically,
                userScrollEnabled = false
            ) {
                FoodSelections(modifier = modifier, mealList = uiState.value.breakfastMealList)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AddMealAppBar(
    modifier: Modifier,
    pagerState: PagerState,
    tabRowItems: List<String>,
    navigateUp: () -> Unit,
    coroutineScope: CoroutineScope
) {
    Column(modifier = modifier.fillMaxWidth()) {
        WecareAppBar(
            modifier = modifier,
            title = "Meals",
            onLeadingIconPress = navigateUp,
            trailingIconRes = R.drawable.ic_search
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

@Composable
private fun FoodSelections(modifier: Modifier, mealList: List<MealsByNutrients>) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(normalPadding),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(normalPadding)
    ) {
        items(mealList.size) { index ->
            val item = mealList[index]
            FoodItem(
                modifier = modifier,
                recipeName = item.title,
                calAmount = item.calories,
                imgUrl = item.imgUrl
            )
        }
    }
}

@Composable
private fun FoodItem(
    modifier: Modifier, recipeName: String, calAmount: Int, imgUrl: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = normalPadding),
        shape = Shapes.medium,
        backgroundColor = MaterialTheme.colors.background,
        elevation = smallElevation
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = modifier
                        .width(maxWidth)
                        .height(maxWidth.times(0.8f))
                        .clip(shape = Shapes.medium),
                    model = imgUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding, horizontal = halfMidPadding),
                text = recipeName,
                style = MaterialTheme.typography.h5,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = halfMidPadding, end = halfMidPadding, bottom = smallPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_fire_calo),
                        contentDescription = null,
                        tint = Red400
                    )
                    Text(
                        text = "$calAmount cal", style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onSecondary.copy(0.5f)
                        )
                    )
                }
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.size(24.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Icon(
                        Icons.Default.Add,
                        modifier = Modifier.size(16.dp),
                        contentDescription = "content description",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}
