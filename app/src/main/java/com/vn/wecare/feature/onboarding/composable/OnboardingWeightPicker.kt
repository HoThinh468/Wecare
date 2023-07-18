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
import com.vn.wecare.utils.WecareUserConstantValues.MAX_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT

@Composable
fun OnboardingWeightPicker(
    modifier: Modifier,
    minWeight: Int = MIN_WEIGHT,
    maxWeight: Int = MAX_WEIGHT,
    weightPicked: Int,
    onPickWeightScrolled: (weight: Int) -> Unit,
    customContent: (@Composable () -> Unit)? = null,
) {
    Card(
        modifier = modifier.width(90.dp),
        shape = RoundedCornerShape(midRadius),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.secondaryVariant
    ) {
        NumberPickerSpinner(
            modifier = modifier.padding(smallPadding),
            max = maxWeight,
            min = minWeight,
            pickerTextSize = 80f,
            onValChange = onPickWeightScrolled
        )
    }
    Spacer(modifier = modifier.height(largePadding))
    if (customContent == null) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = weightPicked.toString(), style = MaterialTheme.typography.h1)
            Text(text = "kg", style = MaterialTheme.typography.body2)
        }
    } else {
        customContent()
    }
}