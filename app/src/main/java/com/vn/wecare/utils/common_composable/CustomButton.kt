package com.vn.wecare.utils.common_composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    padding: Int = 0,
    backgroundColor: Color = MaterialTheme.colors.primary,
    textColor: Color = Color.Black
) {
    Button(
        modifier = Modifier
            .padding(padding.dp)
            .fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Text(text = text, color = textColor)
    }
}