package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.utils.common_composable.IconButtonWithFullWidth

@Composable
fun OnboardingGenderSelection(
    modifier: Modifier
) {
    IconButtonWithFullWidth(
        buttonText = "Male", iconVector = Icons.Filled.Male
    ) { /*TODO*/ }
    Spacer(modifier = modifier.height(halfMidPadding))
    IconButtonWithFullWidth(
        buttonText = "Female",
        iconVector = Icons.Filled.Female,
        buttonColor = MaterialTheme.colors.secondaryVariant,
        contentColor = MaterialTheme.colors.onSecondary
    ) { /*TODO*/ }
    Spacer(modifier = modifier.height(halfMidPadding))
    IconButtonWithFullWidth(
        buttonText = "Prefer not to say",
        iconVector = Icons.Filled.Close,
        buttonColor = MaterialTheme.colors.secondaryVariant,
        contentColor = MaterialTheme.colors.onSecondary
    ) { /*TODO*/ }
}