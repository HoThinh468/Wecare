package com.vn.wecare.feature.food.report

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.feature.home.water.report.NoNetWorkConnectionUI
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FoodReportScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: FoodReportViewModel,
) {

    val nutrientsReportUiState = viewModel.nutrientReportUiState.collectAsState().value

    Scaffold(
        topBar = {
            WecareAppBar(
                modifier = modifier, onLeadingIconPress = navigateBack, title = "Nutrition report"
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(midPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (checkInternetConnection(LocalContext.current)) {
                FoodBarChartReport(
                    modifier = modifier, onNavigateUp = { navigateBack() }, viewModel = viewModel
                )
                Spacer(modifier = modifier.height(midPadding))
                FoodNutrientsReport(
                    modifier = modifier, uiState = nutrientsReportUiState
                )
                Spacer(modifier = modifier.height(xxxExtraPadding))
            } else NoNetWorkConnectionUI(modifier = modifier)
        }
    }
}