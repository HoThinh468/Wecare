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
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.ui.mealdetail.MealDetailInformationBottomSheet
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.feature.food.data.model.MealRecipe
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.utils.common_composable.DynamicErrorDialog
import com.vn.wecare.utils.common_composable.LoadingDialog
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddMealScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: AddMealViewModel,
    index: Int,
    moveToSearchMealScreen: () -> Unit,
) {
    val pagerState = rememberPagerState()

    val tabRowItems = listOf("Breakfast", "Lunch", "Snack", "Dinner")
    val coroutineScope = rememberCoroutineScope()

    val openErrorDialog = remember { mutableStateOf(true) }

    val uiState = viewModel.uiState.collectAsState()

    val breakfastMeals = viewModel.breakfastMeals.collectAsState().value
    val lunchMeals = viewModel.lunchMeals.collectAsState().value
    val snackMeals = viewModel.snackMeals.collectAsState().value
    val dinnerMeals = viewModel.dinnerMeals.collectAsState().value

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

    viewModel.getMealsResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(LocalContext.current, "Load meals successfully!", Toast.LENGTH_SHORT)
                    .show()
            }

            is Response.Error -> {
                Toast.makeText(
                    LocalContext.current, it.e?.message ?: DEFAULT_ERROR_MESSAGE, Toast.LENGTH_SHORT
                ).show()
            }

            else -> { /* do nothing */
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        coroutineScope.launch { pagerState.animateScrollToPage(index) }
    })

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val focusManager = LocalFocusManager.current

    ModalBottomSheetLayout(sheetContent = {
        val mealRecipe = uiState.value.currentChosenMeal ?: MealRecipe()
        MealDetailInformationBottomSheet(mealRecipe = mealRecipe, onCloseBottomSheet = {
            coroutineScope.launch { sheetState.hide() }
        }, onAddMealClick = {
            if (mealRecipe.id != 0L) {
                viewModel.insertMealRecord(
                    dateTime = Calendar.getInstance(),
                    meal = mealRecipe,
                    mealTypeKey = when (pagerState.currentPage) {
                        0 -> MealTypeKey.BREAKFAST
                        1 -> MealTypeKey.LUNCH
                        2 -> MealTypeKey.SNACK
                        else -> MealTypeKey.DINNER
                    }
                )
            }
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
                            0 -> breakfastMeals
                            1 -> lunchMeals
                            2 -> snackMeals
                            else -> dinnerMeals
                        },
                        addMealViewModel = viewModel,
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