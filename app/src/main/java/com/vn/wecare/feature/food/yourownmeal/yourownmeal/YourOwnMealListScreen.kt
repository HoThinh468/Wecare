package com.vn.wecare.feature.food.yourownmeal.yourownmeal

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.ui.NutrientIndexItem
import com.vn.wecare.feature.food.common.AddMealButton
import com.vn.wecare.feature.food.common.FoodErrorMessage
import com.vn.wecare.feature.food.common.NoInternetMessage
import com.vn.wecare.feature.food.data.model.Meal
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.toMealByNutrients
import com.vn.wecare.feature.food.mealdetail.NutrientSubInfoItem
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxExtraPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.common_composable.DynamicErrorDialog
import com.vn.wecare.utils.common_composable.LoadingDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun YourOwnMealListScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: YourOwnMealViewModel,
    moveToAddYourOwnMealScreen: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    val uiState = viewModel.uiState.collectAsState().value

    val openErrorDialog = remember { mutableStateOf(false) }

    val isNetworkAvailable = checkInternetConnection(LocalContext.current)

    uiState.addMealRecordResponse.let {
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

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    },
                )
            },
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            YourOwnMealTopBar(
                modifier = modifier,
                moveToAddYourOwnMealScreen = moveToAddYourOwnMealScreen,
                navigateBack = navigateBack,
                chosenMealTypeKey = uiState.currentCategory ?: MealTypeKey.BREAKFAST,
                onItemClick = { viewModel.onMealCategoryChosen(it) },
                mealName = viewModel.mealName,
                onClearSearchBox = {
                    viewModel.clearMealName()
                },
                onSearchTextChanged = viewModel::onNameChange
            )
        },
    ) {
        if (isNetworkAvailable) {
            uiState.getMealResult.let {
                when (it) {
                    is Response.Loading -> {
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                                .padding(horizontal = xxExtraPadding),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = modifier.padding(vertical = xxxExtraPadding),
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }

                    is Response.Success -> {
                        if (uiState.mealList.isNotEmpty()) {
                            var filteredMeals: List<Meal>
                            LazyColumn(
                                modifier = modifier.padding(horizontal = midPadding)
                            ) {
                                filteredMeals = if (viewModel.mealName.isEmpty()) {
                                    uiState.mealList
                                } else {
                                    val resultList = ArrayList<Meal>()
                                    for (i in uiState.mealList) {
                                        if (i.name.lowercase()
                                                .contains(viewModel.mealName.lowercase())
                                        ) {
                                            resultList.add(i)
                                        }
                                    }
                                    resultList
                                }
                                items(filteredMeals.size) { i ->
                                    val item = filteredMeals[i]
                                    MyOwnMealItem(
                                        modifier = modifier,
                                        onAddBtnClick = {
                                            viewModel.insertMealRecord(
                                                uiState.currentCategory ?: MealTypeKey.BREAKFAST,
                                                item.toMealByNutrients()
                                            )
                                        },
                                        meal = item, moveToEditScreen = {},
                                    )
                                }
                            }
                        } else {
                            NoOwnMealYet(modifier = modifier) {
                                moveToAddYourOwnMealScreen()
                            }
                        }
                    }

                    is Response.Error -> {
                        FoodErrorMessage(modifier = modifier)
                    }

                    else -> { // Do nothing
                    }
                }
            }
        } else {
            NoInternetMessage(modifier = modifier)
        }
    }
}

@Composable
private fun MyOwnMealItem(
    modifier: Modifier,
    onAddBtnClick: () -> Unit,
    meal: Meal,
    moveToEditScreen: () -> Unit,
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = normalPadding)
        ) {
            Row(
                modifier = modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = modifier
                        .height(110.dp)
                        .width(72.dp)
                        .clip(shape = Shapes.medium),
                    model = meal.imgUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(modifier = modifier.padding(start = halfMidPadding)) {
                    Text(
                        modifier = modifier.width(200.dp),
                        text = meal.name,
                        style = MaterialTheme.typography.h5,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = modifier.height(2.dp))
                    NutrientSubInfoItem(
                        modifier = modifier,
                        icon = Icons.Default.LocalFireDepartment,
                        index = "${meal.calories} cal"
                    )
                    Spacer(modifier = modifier.height(2.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        NutrientIndexItem(
                            modifier = modifier,
                            color = Red400,
                            title = "Protein",
                            index = "${meal.protein}g"
                        )
                        NutrientIndexItem(
                            modifier = modifier,
                            color = Yellow,
                            title = "Fat",
                            index = "${meal.fat}g"
                        )
                        NutrientIndexItem(
                            modifier = modifier,
                            color = Blue,
                            title = "Carbs",
                            index = "${meal.carbs}g"
                        )
                    }
                }
            }
            Row(modifier = modifier.align(Alignment.TopEnd)) {
                IconButton(onClick = { /*Move to edit screen*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = modifier.height(smallPadding))
                IconButton(onClick = { onAddBtnClick() }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
        Divider()
    }
}

@Composable
private fun NoOwnMealYet(modifier: Modifier, moveToAddYourOwnMealScreen: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = xxExtraPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "You have not added any meal for this category yet, Add your own meal now",
            textAlign = TextAlign.Center
        )
        AddMealButton(
            modifier = modifier,
            onButtonClick = {
                moveToAddYourOwnMealScreen()
            },
        )
    }
}