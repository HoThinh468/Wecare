package com.vn.wecare.utils.common_composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.R
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun BarChartItem(
    modifier: Modifier = Modifier,
    width: Dp = 20.dp,
    progress: Float = 0f,
    barColor: Color = MaterialTheme.colors.secondary,
    completeBarColor: Color = MaterialTheme.colors.primary,
    index: String? = null,
    onItemClick: () -> Unit = {},
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
        if (index != null) {
            Box(modifier = modifier.background(barColor.copy(alpha = 0.5f))) {
                Text(
                    text = index, style = MaterialTheme.typography.caption.copy(color = colorIcon)
                )
            }
        }
        Box(
            modifier = modifier.weight(9f), contentAlignment = Alignment.BottomCenter
        ) {
            Box(modifier = modifier
                .fillMaxHeight(progress)
                .width(width)
                .padding(bottom = smallPadding)
                .clip(RoundedCornerShape(mediumRadius))
                .background(if (progress == 1f) completeBarColor else barColor)
                .clickable { }) {
                if (progress == 1f) {
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
        if (itemTitle != null) {
            Text(
                modifier = modifier.weight(1f),
                text = itemTitle,
                style = MaterialTheme.typography.caption.copy(titleColor)
            )
        }
    }
}