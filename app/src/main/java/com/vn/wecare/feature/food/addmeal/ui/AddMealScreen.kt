package com.vn.wecare.feature.food.addmeal.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.utils.common_composable.DynamicErrorDialog
import com.vn.wecare.utils.common_composable.LoadingDialog
import kotlinx.coroutines.launch
import java.util.Calendar

val DEFAULT_MEAL_BY_NUTRIENT = MealByNutrients(
    0, "This is a title", "img", "png", 100, "12g", "13g", "20g"
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddMealScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    addMealViewModel: AddMealViewModel,
    index: Int,
    moveToSearchMealScreen: () -> Unit,
    meals: List<LazyPagingItems<MealByNutrients>>
) {
    val pagerState = rememberPagerState()

    val tabRowItems = listOf("Breakfast", "Lunch", "Snack", "Dinner")
    val coroutineScope = rememberCoroutineScope()

    val openErrorDialog = remember { mutableStateOf(true) }

    val uiState = addMealViewModel.uiState.collectAsState()

    uiState.value.insertMealRecordResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(LocalContext.current, "Add meal successfully!", Toast.LENGTH_SHORT)
                    .show()
            }

            is Response.Error -> {
                DynamicErrorDialog(
                    isShowing = openErrorDialog.value,
                    onDismissRequest = { openErrorDialog.value = false },
                    errorMessage = it.e?.message ?: "Oops, something wrong happened"
                )
            }

            else -> { /* do nothing */
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        coroutineScope.launch { pagerState.animateScrollToPage(index) }
    })

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val focusManager = LocalFocusManager.current

    ModalBottomSheetLayout(sheetContent = {
        MealDetailInformationBottomSheet(mealByNutrients = uiState.value.currentChosenMeal
            ?: DEFAULT_MEAL_BY_NUTRIENT,
            onCloseBottomSheet = {
                coroutineScope.launch { sheetState.hide() }
            },
            onAddMealClick = {
                addMealViewModel.insertMealRecord(
                    dateTime = Calendar.getInstance(),
                    meal = uiState.value.currentChosenMeal ?: DEFAULT_MEAL_BY_NUTRIENT,
                    mealTypeKey = when (pagerState.currentPage) {
                        0 -> MealTypeKey.BREAKFAST
                        1 -> MealTypeKey.LUNCH
                        2 -> MealTypeKey.SNACK
                        else -> MealTypeKey.DINNER
                    }
                )
            })
    }, sheetState = sheetState) {
        Scaffold(topBar = {
            AddMealAppBar(
                modifier = modifier,
                pagerState = pagerState,
                tabRowItems = tabRowItems,
                coroutineScope = coroutineScope,
                navigateUp = navigateUp,
                moveToSearchMealScreen = moveToSearchMealScreen
            )
        }) {
            Column(modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }) {
                HorizontalPager(
                    state = pagerState,
                    modifier = modifier.fillMaxWidth(),
                    pageCount = tabRowItems.size,
                    verticalAlignment = Alignment.CenterVertically,
                    userScrollEnabled = false
                ) {
                    MealsGridView(modifier = modifier,
                        mealList = when (pagerState.currentPage) {
                            0 -> meals[0]
                            1 -> meals[1]
                            2 -> meals[2]
                            else -> meals[3]
                        },
                        addMealViewModel = addMealViewModel,
                        mealTypeKey = when (pagerState.currentPage) {
                            0 -> MealTypeKey.BREAKFAST
                            1 -> MealTypeKey.LUNCH
                            2 -> MealTypeKey.SNACK
                            else -> MealTypeKey.DINNER
                        },
                        openBottomSheet = {
                            coroutineScope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.hide()
                                } else {
                                    sheetState.show()
                                }
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun LoadingDataErrorUI(modifier: Modifier, message: String) {
    Column(modifier.padding(mediumPadding), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(id = R.drawable.img_oops),
            contentDescription = null
        )
        Text(message, textAlign = TextAlign.Center)
    }
}