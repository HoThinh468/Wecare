package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnboardingItemPicker(
    modifier: Modifier,
    onClick: () -> Unit,
    title: String,
    subtitle: String? = null,
    enabled: Boolean = true,
    borderColor: Color = MaterialTheme.colors.secondary,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        border = BorderStroke(width = 2.dp, color = borderColor),
        shape = Shapes.small
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(midPadding)
        ) {
            Text(text = title, style = MaterialTheme.typography.h5)
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.button.copy(MaterialTheme.colors.secondary)
                )
            }
        }
    }
}