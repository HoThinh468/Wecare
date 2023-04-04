package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.training.widget.numberPickerSpinner
import com.vn.wecare.ui.theme.largePadding
import com.vn.wecare.ui.theme.smallRadius

@Composable
fun OnboardingAgePicker(
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(smallRadius),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.secondaryVariant
    ) {
        numberPickerSpinner(modifier = modifier, max = 100, min = 6)
    }
    Spacer(modifier = modifier.height(largePadding))
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(text = "21", style = MaterialTheme.typography.h1)
        Text(text = "y/o", style = MaterialTheme.typography.body2)
    }
}