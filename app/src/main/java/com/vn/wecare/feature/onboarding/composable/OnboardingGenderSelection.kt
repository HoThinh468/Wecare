package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
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
    modifier: Modifier,
    onGenderSelect: (id: Int) -> Unit,
    selectedGender: Int
) {
    IconButtonWithFullWidth(
        buttonText = "Male",
        iconVector = Icons.Filled.Male,
        buttonColor = if (selectedGender == 0) MaterialTheme.colors.primary
        else MaterialTheme.colors.secondaryVariant,
        contentColor = if (selectedGender == 0) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSecondary
    ) {
        onGenderSelect(0)
    }
    Spacer(modifier = modifier.height(halfMidPadding))
    IconButtonWithFullWidth(
        buttonText = "Female",
        iconVector = Icons.Filled.Female,
        buttonColor = if (selectedGender == 1) MaterialTheme.colors.primary
        else MaterialTheme.colors.secondaryVariant,
        contentColor = if (selectedGender == 1) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSecondary
    ) {
        onGenderSelect(1)
    }
}