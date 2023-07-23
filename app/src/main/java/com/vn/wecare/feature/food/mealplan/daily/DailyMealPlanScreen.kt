package com.vn.wecare.feature.food.mealplan.daily

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.ui.mealdetail.MealDetailInformationBottomSheet
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.Recipe.RecipeInformation
import com.vn.wecare.feature.food.data.model.Recipe.toMealRecipe
import com.vn.wecare.feature.food.mealdetail.NutrientSubInfoItem
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.LoadingDialog
import kotlinx.coroutines.launch
import java.time.LocalDate

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DailyMealPlanScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: DailyMealPlanViewModel,
    localDate: LocalDate
) {

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsState().value

    uiState.getMealPlanResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                viewModel.resetGetMealResponse()
            }

            is Response.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    it.e?.message ?: "Error! Cannot get meal plan!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetGetMealResponse()
            }

            else -> { /* do nothing */
            }
        }
    }

    uiState.addMealResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(
                    LocalContext.current, "Add to your records successfully!", Toast.LENGTH_SHORT
                ).show()
                viewModel.resetGetMealResponse()
            }

            is Response.Error -> {
                Toast.makeText(
                    LocalContext.current, "Error! Cannot add meal!", Toast.LENGTH_SHORT
                ).show()
                viewModel.resetGetMealResponse()
            }

            else -> { /* do nothing */
            }
        }
    }

    ModalBottomSheetLayout(sheetContent = {
        MealDetailInformationBottomSheet(
            mealRecipe = uiState.currentChosenRecipe.toMealRecipe(),
            onCloseBottomSheet = {
                coroutineScope.launch { sheetState.hide() }
            },
            onAddMealClick = {
                viewModel.onAddRecipeClick(
                    uiState.currentChosenRecipe, uiState.currentChosenMealType
                )
            },
        )
    }, sheetState = sheetState) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                DailyMealPlanAppBar(
                    modifier = modifier,
                    navigateUp = navigateUp,
                    totalCalories = uiState.totalCalories,
                    totalProtein = uiState.totalProtein,
                    totalFat = uiState.totalFat,
                    totalCarbs = uiState.totalCarbs,
                    localDate = localDate
                )
            },
        ) {
            if (uiState.dailyMealPlan.size >= 3) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(normalPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Breakfast",
                        style = MaterialTheme.typography.h5,
                        modifier = modifier.padding(top = normalPadding, bottom = halfMidPadding)
                    )
                    DailyMealPlanItem(modifier = modifier, onClick = {
                        coroutineScope.launch { sheetState.show() }
                        viewModel.onRecipeClick(uiState.dailyMealPlan[0], MealTypeKey.BREAKFAST)
                    }, recipe = uiState.dailyMealPlan[0], onAddBtnClick = {
                        viewModel.onAddRecipeClick(
                            uiState.dailyMealPlan[0], MealTypeKey.BREAKFAST
                        )
                    }, isAddMealEnabled = localDate == LocalDate.now())
                    Text(
                        text = "Lunch",
                        style = MaterialTheme.typography.h5,
                        modifier = modifier.padding(top = normalPadding, bottom = halfMidPadding)
                    )
                    DailyMealPlanItem(modifier = modifier, onClick = {
                        coroutineScope.launch { sheetState.show() }
                        viewModel.onRecipeClick(uiState.dailyMealPlan[1], MealTypeKey.LUNCH)
                    }, recipe = uiState.dailyMealPlan[1], onAddBtnClick = {
                        viewModel.onAddRecipeClick(
                            uiState.dailyMealPlan[1], MealTypeKey.LUNCH
                        )
                    }, isAddMealEnabled = localDate == LocalDate.now())
                    Text(
                        text = "Dinner",
                        style = MaterialTheme.typography.h5,
                        modifier = modifier.padding(top = normalPadding, bottom = halfMidPadding)
                    )
                    DailyMealPlanItem(modifier = modifier, onClick = {
                        coroutineScope.launch { sheetState.show() }
                        viewModel.onRecipeClick(uiState.dailyMealPlan[2], MealTypeKey.DINNER)
                    }, recipe = uiState.dailyMealPlan[2], onAddBtnClick = {
                        viewModel.onAddRecipeClick(
                            uiState.dailyMealPlan[2], MealTypeKey.DINNER
                        )
                    }, isAddMealEnabled = localDate == LocalDate.now())
                    Spacer(modifier = modifier.height(halfMidPadding))
                }
            } else {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(midPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_oops),
                        contentDescription = null
                    )
                    Text(
                        text = "Oops! We find no meal plan for you, please try again, later",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DailyMealPlanItem(
    modifier: Modifier,
    onClick: () -> Unit,
    recipe: RecipeInformation,
    onAddBtnClick: () -> Unit,
    isAddMealEnabled: Boolean
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.medium,
        elevation = smallElevation,
        onClick = onClick
    ) {
        Box(
            modifier
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            ) {
                AsyncImage(
                    modifier = modifier
                        .width(100.dp)
                        .height(120.dp)
                        .clip(Shapes.medium),
                    model = recipe.image,
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = modifier.width(normalPadding))
                Column(modifier = modifier.fillMaxWidth()) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                        minLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = modifier.height(smallPadding))
                    Text(
                        text = "Ready in mutes: ${recipe.readyInMinutes}",
                        style = MaterialTheme.typography.caption
                    )
                    Spacer(modifier = modifier.height(smallPadding))
                    NutrientSubInfoItem(
                        modifier = modifier,
                        icon = Icons.Default.LocalFireDepartment,
                        color = Red400,
                        index = "${recipe.nutrition.nutrients[0].amount.toInt()} cal"
                    )
                }
            }
            Button(
                modifier = modifier.align(Alignment.BottomEnd), onClick = {
                    onAddBtnClick()
                }, shape = RoundedCornerShape(mediumRadius), enabled = isAddMealEnabled
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colors.onPrimary
                )
                Text("Add")
            }
        }
    }
}