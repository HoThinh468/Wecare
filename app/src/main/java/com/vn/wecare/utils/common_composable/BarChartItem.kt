package com.vn.wecare.utils.common_composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun BarChartItem(
    modifier: Modifier = Modifier,
    width: Dp = 20.dp,
    progress: Float = 0f,
    barColor: Color = MaterialTheme.colors.secondary,
    completeBarColor: Color = MaterialTheme.colors.primary,
    index: Int = 0,
    itemTitle: String? = null,
    titleColor: Color = MaterialTheme.colors.onBackground,
    @DrawableRes bottomIconRes: Int = R.drawable.ic_done,
    colorIcon: Color = MaterialTheme.colors.onPrimary
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = modifier.weight(9f), contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (index != 0) {
                    Text(
                        text = index.toString(),
                        style = MaterialTheme.typography.caption.copy(color = titleColor),
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = modifier
                        .fillMaxHeight(progress)
                        .width(width)
                        .padding(vertical = smallPadding)
                        .clip(RoundedCornerShape(mediumRadius))
                        .background(if (progress >= 1f) completeBarColor else barColor)
                ) {
                    if (progress >= 1f) {
                        Icon(
                            modifier = modifier
                                .size(20.dp)
                                .align(Alignment.BottomCenter),
                            painter = painterResource(id = bottomIconRes),
                            contentDescription = null,
                            tint = colorIcon
                        )
                    }
                }
            }
        }
        if (itemTitle != null) {
            Text(
                modifier = modifier.weight(1f),
                text = itemTitle,
                style = MaterialTheme.typography.body2.copy(titleColor)
            )
        }
    }
}