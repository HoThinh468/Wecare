package com.vn.wecare.utils.common_composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.vn.wecare.ui.theme.normalPadding

@Composable
fun CustomTextField(
    hint: String?,
    label: String?,
    backgroundColor: Color? = null,
    cursorColor: Color? = null,
    focusedIndicatorColor: Color? = null,
    padding: Dp = normalPadding,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    value: String,
    onValueChange: (String) -> Unit,
    isTextVisible: Boolean = true,
    onShowTextClick: () -> Unit = {}
) {
    TextField(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { if (hint != null) Text(text = hint) },
        label = { if (label != null) Text(text = label) },
        singleLine = true,
        visualTransformation = if (isTextVisible) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "leadingIcon",
                    tint = MaterialTheme.colors.primary,
                )

            }
        },
        trailingIcon = {
            if (trailingIcon != null) {
                IconButton(onClick = onShowTextClick) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = "trailingIcon",
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor ?: DefaultTintColor,
            cursorColor = cursorColor ?: DefaultTintColor,
            focusedIndicatorColor = focusedIndicatorColor ?: DefaultTintColor
        )
    )
}