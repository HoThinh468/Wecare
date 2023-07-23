package com.vn.wecare.feature.food.mealplan.daily

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.model.mealplan.MealPlan
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.WecareUserConstantValues.SPOONACULAR_IMAGE_URL
import com.vn.wecare.utils.common_composable.LoadingDialog
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DailyMealPlanScreen(
    modifier: Modifier = Modifier, navigateUp: () -> Unit, viewModel: DailyMealPlanViewModel
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

    ModalBottomSheetLayout(sheetContent = {
//        MealDetailInformationBottomSheet(mealRecipe = uiState.mealRecipe, onCloseBottomSheet = {
//            coroutineScope.launch { sheetState.hide() }
//        }, onAddMealClick = {}, showAddMealButton = false)
    }, sheetState = sheetState) {
        Scaffold(modifier = modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                DailyMealPlanAppBar(
                    modifier = modifier,
                    navigateUp = navigateUp,
                    totalCalories = uiState.totalCalories,
                    totalProtein = uiState.totalProtein,
                    totalFat = uiState.totalFat,
                    totalCarbs = uiState.totalCarbs
                )
            },
            bottomBar = {
                Button(
                    modifier = modifier
                        .padding(midPadding)
                        .fillMaxWidth()
                        .height(40.dp),
                    onClick = { },
                    shape = Shapes.large,
                ) {
                    Icon(
                        modifier = modifier
                            .padding(end = smallPadding)
                            .size(16.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                    Text(text = "Choose this plan", style = MaterialTheme.typography.button)
                }
            }) {
            if (uiState.dailyMealPlan.meals.size >= 3) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(
                            start = midPadding,
                            end = midPadding,
                            top = normalPadding,
                            bottom = xxxExtraPadding
                        )
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Breakfast",
                        style = MaterialTheme.typography.h5,
                        modifier = modifier.padding(top = normalPadding, bottom = halfMidPadding)
                    )
                    DailyMealPlanItem(
                        modifier = modifier,
                        onClick = { /*TODO*/ },
                        mealPlan = uiState.dailyMealPlan.meals[0]
                    )
                    Text(
                        text = "Lunch",
                        style = MaterialTheme.typography.h5,
                        modifier = modifier.padding(top = normalPadding, bottom = halfMidPadding)
                    )
                    DailyMealPlanItem(
                        modifier = modifier,
                        onClick = { /*TODO*/ },
                        mealPlan = uiState.dailyMealPlan.meals[1]
                    )
                    Text(
                        text = "Dinner",
                        style = MaterialTheme.typography.h5,
                        modifier = modifier.padding(top = normalPadding, bottom = halfMidPadding)
                    )
                    DailyMealPlanItem(
                        modifier = modifier,
                        onClick = { /*TODO*/ },
                        mealPlan = uiState.dailyMealPlan.meals[2]
                    )
                }
            } else {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                }
            }
        }
    }
}

@Composable
fun DailyMealPlanItem(modifier: Modifier, onClick: () -> Unit, mealPlan: MealPlan) {
    Card(modifier = modifier.fillMaxWidth(), shape = Shapes.medium, elevation = smallElevation) {
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
                        .size(100.dp)
                        .clip(Shapes.medium),
                    model = "${SPOONACULAR_IMAGE_URL}${mealPlan.image}",
                    contentDescription = mealPlan.title,
                    contentScale = ContentScale.Crop
                )
                Column(modifier = modifier.fillMaxWidth()) {
                    Text(text = mealPlan.title, style = MaterialTheme.typography.body1)
                    Spacer(modifier = modifier.height(smallPadding))
                    Text(
                        text = "Ready in mutes: ${mealPlan.readyInMinutes}",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            Button(
                modifier = modifier.align(Alignment.BottomEnd),
                onClick = { },
                shape = RoundedCornerShape(mediumRadius)
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