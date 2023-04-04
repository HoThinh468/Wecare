package com.vn.wecare.utils.common_composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun IconButtonWithFullWidth(
    modifier: Modifier = Modifier,
    buttonText: String,
    iconVector: ImageVector? = null,
    @DrawableRes iconRes: Int? = null,
    buttonColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick, modifier = modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor, contentColor = contentColor
        )
    ) {
        if (iconVector != null) {
            Icon(imageVector = iconVector, contentDescription = null)
        }
        if (iconRes != null) {
            Icon(painter = painterResource(id = iconRes), contentDescription = null)
        }
        Text(
            modifier = modifier.padding(
                start = if (iconRes != null || iconVector != null) smallPadding else 0.dp
            ), text = buttonText, style = MaterialTheme.typography.button
        )
    }
}