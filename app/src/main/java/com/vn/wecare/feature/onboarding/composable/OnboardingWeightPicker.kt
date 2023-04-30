package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.onboarding.viewmodel.OnboardingViewModel
import com.vn.wecare.feature.training.widget.numberPickerSpinner
import com.vn.wecare.ui.theme.largePadding
import com.vn.wecare.ui.theme.midRadius
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.WecareUserConstantValues.MAX_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT

@Composable
fun OnboardingWeightPicker(modifier: Modifier, viewModel: OnboardingViewModel) {

    val uiState = viewModel.onboardingUiState.collectAsState()

    Card(
        modifier = modifier.width(90.dp),
        shape = RoundedCornerShape(midRadius),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.secondaryVariant
    ) {
        numberPickerSpinner(
            modifier = modifier.padding(smallPadding),
            max = MAX_WEIGHT,
            min = MIN_WEIGHT,
            pickerTextSize = 80f,
            onValChange = viewModel::onPickWeightScroll
        )
    }
    Spacer(modifier = modifier.height(largePadding))
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(text = uiState.value.weightPicker.toString(), style = MaterialTheme.typography.h1)
        Text(text = "kg", style = MaterialTheme.typography.body2)
    }
}