package com.vn.wecare.utils.common_composable

import androidx.compose.foundation.layout.Column
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
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    hint: String?,
    label: String?,
    backgroundColor: Color? = null,
    cursorColor: Color = MaterialTheme.colors.primary,
    padding: Dp = normalPadding,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    value: String,
    onValueChange: (String) -> Unit,
    isTextVisible: Boolean = true,
    focusedBorderColor: Color = MaterialTheme.colors.primary,
    isError: Boolean = false,
    errorMessage: String? = null,
    onShowTextClick: () -> Unit = {},
) {
    Column {
        OutlinedTextField(
            modifier = modifier
                .padding(start = padding, end = padding, top = padding)
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
                        tint = if (isError) MaterialTheme.colors.error else MaterialTheme.colors.primary,
                    )

                }
            },
            isError = isError,
            trailingIcon = {
                if (trailingIcon != null) {
                    IconButton(onClick = onShowTextClick) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = "trailingIcon",
                            tint = if (isError) MaterialTheme.colors.error else MaterialTheme.colors.primary,
                        )
                    }
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = backgroundColor ?: DefaultTintColor,
                cursorColor = cursorColor,
                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = MaterialTheme.colors.error,
            ),
        )
        if (isError) {
            Text(
                modifier = Modifier.padding(start = padding, bottom = halfMidPadding, top = smallPadding),
                text = errorMessage ?: "Invalid input",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption
            )
        }
    }
}