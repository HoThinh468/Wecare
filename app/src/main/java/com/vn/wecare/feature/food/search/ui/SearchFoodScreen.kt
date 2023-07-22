package com.vn.wecare.feature.food.search.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.ui.DEFAULT_ERROR_MESSAGE
import com.vn.wecare.feature.food.addmeal.ui.FoodCardItemForGridView
import com.vn.wecare.feature.food.addmeal.ui.LoadingDataErrorUI
import com.vn.wecare.feature.food.addmeal.ui.mealdetail.MealDetailInformationBottomSheet
import com.vn.wecare.feature.food.data.model.toMealRecipe
import com.vn.wecare.feature.food.search.SearchFoodViewModel
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.common_composable.DynamicErrorDialog
import com.vn.wecare.utils.common_composable.LoadingDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchFoodScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    moveToAddYourOwnMealScreen: () -> Unit,
    viewModel: SearchFoodViewModel,
) {

    val uiState = viewModel.searchMealUiSate.collectAsState()

    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()

    val openErrorDialog = remember { mutableStateOf(false) }

    val openChooseMealTypeKeyDialog = remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val searchResults = viewModel.searchResults.collectAsState().value

    uiState.value.addMealRecordResult.let {
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

    if (openChooseMealTypeKeyDialog.value) {
        ChooseMealTypeKeyDialog(onAddButtonClick = {
            viewModel.insertMealRecord(
                mealTypeKey = uiState.value.currentChosenMealType,
                meal = uiState.value.currentChosenMeal
            )
        }, onCloseButtonClick = {
            openChooseMealTypeKeyDialog.value = false
        }, viewModel = viewModel
        )
    }

    ModalBottomSheetLayout(sheetContent = {
        MealDetailInformationBottomSheet(
            mealRecipe = uiState.value.currentChosenMeal,
            onCloseBottomSheet = {
                coroutineScope.launch { sheetState.hide() }
            },
            onAddMealClick = {
                openChooseMealTypeKeyDialog.value = true
            },
        )
    }, sheetState = sheetState) {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                SearchMealAppBar(
                    modifier = modifier, onBackPress = {
                        navigateBack()
                    }, viewModel = viewModel
                )
            },
        ) {
            when (uiState.value.loadDataResult) {
                is Response.Success -> {
                    if (searchResults.isNotEmpty()) {
                        LazyVerticalGrid(
                            modifier = modifier
                                .fillMaxSize()
                                .padding(normalPadding),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(normalPadding)
                        ) {
                            items(searchResults.size) { index ->
                                val item = searchResults[index].toMealRecipe()
                                FoodCardItemForGridView(
                                    modifier = modifier,
                                    meal = item,
                                    onCardClick = {
                                        coroutineScope.launch {
                                            if (sheetState.isVisible) {
                                                sheetState.hide()
                                            } else {
                                                sheetState.show()
                                            }
                                        }
                                        viewModel.updateCurrentChosenMeal(item)
                                    },
                                    onAddMealClick = {
                                        viewModel.updateCurrentChosenMeal(item)
                                        openChooseMealTypeKeyDialog.value = true
                                    },
                                )
                            }
                        }
                    } else {
                        NoMealAvailableAddYourOwnMeal(modifier = modifier,
                            moveToAddYourOwnMealScreen = { moveToAddYourOwnMealScreen() })
                    }
                }

                is Response.Error -> {
                    LoadingDataErrorUI(modifier = modifier, DEFAULT_ERROR_MESSAGE)
                }

                is Response.Loading -> {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = modifier.padding(midPadding),
                            color = MaterialTheme.colors.primary
                        )
                    }
                }

                else -> {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            modifier = modifier.size(150.dp),
                            painter = painterResource(id = R.drawable.img_search_illustration),
                            contentDescription = null
                        )
                        Spacer(modifier = modifier.height(midPadding))
                        Text(text = "Search your meal above to view results")
                    }
                }
            }
        }
    }
}