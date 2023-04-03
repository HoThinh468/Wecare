package com.vn.wecare.utils.common_composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.vn.wecare.ui.theme.Black900
import com.vn.wecare.ui.theme.largePadding

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    padding: Dp = largePadding,
    backgroundColor: Color = MaterialTheme.colors.primary,
    textColor: Color = Black900,
    isEnabled: Boolean = true,
) {
    Button(
        contentPadding = PaddingValues(),
        modifier = modifier
            .padding(padding)
            .fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        enabled = isEnabled
    ) {
        Text(text = text, color = textColor)
    }
}