package com.vn.wecare.utils.common_composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
        elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        enabled = isEnabled
    ) {
        Text(text = text, color = textColor)
    }
}