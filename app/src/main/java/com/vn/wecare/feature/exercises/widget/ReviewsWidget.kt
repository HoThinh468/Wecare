package com.vn.wecare.feature.exercises.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vn.wecare.core.model.ListReviewsItem
import com.vn.wecare.ui.theme.Black900
import com.vn.wecare.ui.theme.Grey100
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.YellowStar
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.tinyPadding
import com.vn.wecare.utils.convertMonthAgoTimeStamp

@Preview(showBackground = true)
@Composable
fun Preview() {
    ReviewsWidget(
        reviewsItem = ListReviewsItem(
            userName = "Thinh Ho",
            rating = 4,
            createdDate = 1676300598000,
            likedNumber = 23,
            content = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum"
        )
    )
}

@Composable
fun ReviewsWidget(
    modifier: Modifier = Modifier,
    reviewsItem: ListReviewsItem
) {
    Column(
        modifier = modifier
            .padding(midPadding)
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = modifier
                .height(halfMidPadding)
                .background(Grey500)
        )
        Row(
            modifier = modifier
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier.padding(smallPadding),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                tint = Grey500
            )
            Column(
                modifier = modifier.weight(6f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = modifier.padding(bottom = tinyPadding),
                    text = reviewsItem.userName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = WeCareTypography.button
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingStar(rating = reviewsItem.rating)
                    Text(
                        modifier = modifier
                            .padding(start = smallPadding)
                            .wrapContentHeight(),
                        text = convertMonthAgoTimeStamp(reviewsItem.createdDate),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = WeCareTypography.caption,
                        color = Grey500
                    )
                }
            }
            Spacer(modifier = modifier.weight(0.5f))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = modifier
                        .size(16.dp)
                        .padding(end = tinyPadding)
                )
                Text(
                    text = "(${reviewsItem.likedNumber})",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = WeCareTypography.caption
                )
                Spacer(modifier = modifier.width(mediumPadding))
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = modifier.size(12.dp)
                )
            }
        }
        ExpandableText(text = reviewsItem.content, textColor = Color.Black, minimizedMaxLines = 4)
    }
}

@Composable
fun RatingStar(
    modifier: Modifier = Modifier,
    rating: Int
) {
    Row(
        modifier = modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until rating) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "Star",
                modifier = modifier
//                                        .padding(end = 2.dp)
                    .size(12.dp),
                tint = YellowStar
            )
        }
        for (i in rating until 5) {
            Icon(
                Icons.Default.StarBorder,
                contentDescription = "Star",
                modifier = modifier
                    .size(12.dp),
//                                        .padding(end = 2.dp),
                tint = White
            )
        }
    }
}