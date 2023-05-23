package com.vn.wecare.feature.food.dinner

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.common.AddMealButton
import com.vn.wecare.feature.food.common.MealRecordSection
import com.vn.wecare.feature.food.common.MealTypeOverviewNutrition
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DinnerScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    moveToAddMealScreen: (index: Int) -> Unit,
    navigateToDetailScreen: (meal: MealRecordModel) -> Unit,
    viewModel: DinnerViewModel
) {

    val uiState = viewModel.uiState.collectAsState()

    uiState.value.updateMealRecordResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(LocalContext.current, "Update successfully", Toast.LENGTH_SHORT)
                    .show()
                viewModel.resetUpdateRecordResponse()
            }

            is Response.Error -> {
                Toast.makeText(LocalContext.current, "Update fail", Toast.LENGTH_SHORT).show()
                viewModel.resetUpdateRecordResponse()
            }

            else -> { /* do nothing */
            }
        }
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            WecareAppBar(
                modifier = modifier, title = "Dinner", onLeadingIconPress = navigateUp
            )
        },
        bottomBar = {
            AddMealButton(modifier = modifier, isEnable = uiState.value.isAddMealEnable) {
                moveToAddMealScreen(3)
            }
        }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = midPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(smallPadding))
            MealTypeOverviewNutrition(
                modifier = modifier,
                mealUiState = uiState.value,
                onDateChangeListener = viewModel::onDateChangeListener
            )
            Spacer(modifier = modifier.height(midPadding))
            MealRecordSection(
                modifier = modifier,
                mealUiState = uiState.value,
                navigateToDetailScreen = navigateToDetailScreen,
                deleteMealRecord = viewModel::deleteMealRecord,
                onMinusClick = viewModel::onMealRecordItemMinusClick,
                onAddClick = viewModel::onMealRecordItemPlusClick
            )
        }
    }
}