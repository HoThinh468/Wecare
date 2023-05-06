package com.vn.wecare.utils

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vn.wecare.R

@Composable
fun CustomOutlinedIconButton(
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    @StringRes trainingTitleRes: Int? = null,
    @ColorRes colorRes: Int = R.color.Green500,
    buttonSize: Dp = 50.dp,
    iconSize: Dp = 24.dp,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = modifier
                .size(buttonSize)
                .background(color = colorResource(id = colorRes), shape = CircleShape)
        ) {
            Icon(
                painterResource(id = iconRes),
                contentDescription = null,
                modifier = modifier.size(iconSize),
                tint = colorResource(id = R.color.white)
            )
        }
        if (trainingTitleRes != null) {
            Text(
                modifier = modifier.padding(top = 4.dp),
                text = stringResource(id = trainingTitleRes),
                style = MaterialTheme.typography.button.copy(
                    color = colorResource(id = R.color.Black450)
                )
            )
        }
    }
}