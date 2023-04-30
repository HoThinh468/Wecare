package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.onboarding.viewmodel.OnboardingViewModel
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.utils.common_composable.IconButtonWithFullWidth

@Composable
fun OnboardingGenderSelection(
    modifier: Modifier, viewModel: OnboardingViewModel
) {

    val uiState = viewModel.onboardingUiState.collectAsState()

    IconButtonWithFullWidth(
        buttonText = "Male",
        iconVector = Icons.Filled.Male,
        buttonColor = if (uiState.value.genderSelectionId == 0) MaterialTheme.colors.primary
        else MaterialTheme.colors.secondaryVariant,
        contentColor = if (uiState.value.genderSelectionId == 0) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSecondary
    ) {
        viewModel.onGenderSelect(0)
    }
    Spacer(modifier = modifier.height(halfMidPadding))
    IconButtonWithFullWidth(
        buttonText = "Female",
        iconVector = Icons.Filled.Female,
        buttonColor = if (uiState.value.genderSelectionId == 1) MaterialTheme.colors.primary
        else MaterialTheme.colors.secondaryVariant,
        contentColor = if (uiState.value.genderSelectionId == 1) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSecondary
    ) {
        viewModel.onGenderSelect(1)
    }
    Spacer(modifier = modifier.height(halfMidPadding))
    IconButtonWithFullWidth(
        buttonText = "Prefer not to say",
        iconVector = Icons.Filled.Close,
        buttonColor = if (uiState.value.genderSelectionId == 2) MaterialTheme.colors.primary
        else MaterialTheme.colors.onPrimary,
        contentColor = if (uiState.value.genderSelectionId == 2) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSecondary
    ) {
        viewModel.onGenderSelect(2)
    }
}