package com.vn.wecare.utils.common_composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R

@Composable
fun WecareAppBar(
    modifier: Modifier,
    @DrawableRes leadingIconRes: Int = R.drawable.ic_arrow_back,
    onLeadingIconPress: () -> Unit = {},
    @DrawableRes trailingIconRes: Int? = null,
    onTrailingIconPress: () -> Unit = {},
    title: String = "Wecare",
    backgroundColor: Color = MaterialTheme.colors.background,
    onBackgroundColor: Color = MaterialTheme.colors.onBackground
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = backgroundColor)
    ) {
        IconButton(
            modifier = modifier.align(Alignment.CenterStart), onClick = onLeadingIconPress
        ) {
            Icon(
                painter = painterResource(id = leadingIconRes),
                contentDescription = null,
                tint = onBackgroundColor
            )
        }
        Text(
            modifier = modifier.align(Alignment.Center),
            text = title,
            style = MaterialTheme.typography.h4.copy(color = onBackgroundColor)
        )
        if (trailingIconRes != null) {
            IconButton(
                modifier = modifier.align(Alignment.CenterEnd), onClick = onTrailingIconPress
            ) {
                Icon(
                    painter = painterResource(id = trailingIconRes),
                    contentDescription = null,
                    tint = onBackgroundColor
                )
            }
        }
    }
}