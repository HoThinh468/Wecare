package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.training.widget.NumberPickerSpinner
import com.vn.wecare.ui.theme.largePadding
import com.vn.wecare.ui.theme.midRadius
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.WecareUserConstantValues.MAX_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT

@Composable
fun OnboardingHeightPicker(
    modifier: Modifier, heightPicker: Int, onPickHeightScroll: (height: Int) -> Unit
) {
    Card(
        modifier = modifier.width(90.dp),
        shape = RoundedCornerShape(midRadius),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.secondaryVariant
    ) {
        NumberPickerSpinner(
            modifier = modifier.padding(smallPadding),
            max = MAX_HEIGHT,
            min = MIN_HEIGHT,
            pickerTextSize = 80f,
            onValChange = onPickHeightScroll
        )
    }
    Spacer(modifier = modifier.height(largePadding))
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(text = heightPicker.toString(), style = MaterialTheme.typography.h1)
        Text(text = "cm", style = MaterialTheme.typography.body2)
    }
}