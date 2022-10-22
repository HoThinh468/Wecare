package com.vn.wecare.utils.common_composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardListTile(
    modifier: Modifier,
    onClick: () -> Unit = {},
    leadingIconRes: Int?,
    trailingIconRes: Int?,
    titleRes: Int,
    subTitle: String?,
    colorIconRes: Int = R.color.Green500,
    elevation: Dp?
    ) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = Shapes.small,
        onClick = onClick,
        elevation = elevation ?: 1.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = normalPadding, vertical = normalPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = modifier
                    .widthIn()
                    .weight(0.9f)
                    .padding(end = normalPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIconRes != null) {
                    Icon(
                        painter = painterResource(id = leadingIconRes),
                        contentDescription = null,
                        tint = colorResource(id = colorIconRes),
                        modifier = modifier.padding(end = mediumPadding)
                    )
                }
                Column {
                    Text(
                        text = stringResource(id = titleRes),
                        style = MaterialTheme.typography.body1
                    )
                    if (subTitle != null) {
                        Text(
                            text = subTitle,
                            style = MaterialTheme.typography.caption,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            if (trailingIconRes != null) {
                Icon(
                    painter = painterResource(id = trailingIconRes),
                    contentDescription = null,
                    modifier = modifier
                        .size(smallIconSize)
                        .weight(0.1f)
                )
            }
        }
    }
}