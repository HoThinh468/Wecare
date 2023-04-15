package com.vn.wecare.feature.exercises.widget

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.core.model.ListReviewsItem
import com.vn.wecare.feature.exercises.program_detail.ProgramDetailViewModel
import com.vn.wecare.feature.exercises.program_ratings.ProgramRatingsViewModel
import com.vn.wecare.feature.training.dashboard.widget.ProgressBar
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.convertMonthAgoTimeStamp

@Composable
fun ReviewsWidget(
    modifier: Modifier = Modifier,
    reviewsItem: ListReviewsItem,
    exerciseType: ExerciseType,
    exerciseIndex: Int,
    reviewIndex: Int,
    reviewLikeCount: Int,
    viewModel: ProgramRatingsViewModel = hiltViewModel(),
    detailViewModel: ProgramDetailViewModel = hiltViewModel()
) {
//    viewModel.getLikeCountResponse(exerciseType, exerciseIndex, reviewIndex)
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
                    RatingStar(rating = reviewsItem.rate)
                    Text(
                        modifier = modifier
                            .padding(start = smallPadding)
                            .wrapContentHeight(),
                        text = convertMonthAgoTimeStamp(reviewsItem.time),
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

                IconButton(
                    onClick = {
                        viewModel.likeReview(
                            exerciseType,
                            exerciseIndex,
                            reviewIndex,
                            reviewLikeCount + 1
                        ) {
                            detailViewModel.getListReview(exerciseType, exerciseIndex)
                            detailViewModel.listReviews.value?.let {
                                viewModel.setListReview(
                                    it
                                )
                            }
                        }
                    }
                ) {
                    if (viewModel.checkIsLiked(reviewIndex)) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "",
                            tint = Green500,
                            modifier = modifier
                                .size(16.dp)
                                .padding(end = tinyPadding)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = modifier
                                .size(16.dp)
                                .padding(end = tinyPadding)
                        )
                    }
                }
                Text(
                    text = "(${reviewLikeCount})",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = WeCareTypography.caption
                )
            }
            Spacer(modifier = modifier.width(mediumPadding))
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "",
                tint = Color.Black,
                modifier = modifier.size(12.dp)
            )
        }
    }
    ExpandableText(
        modifier = modifier.padding(start = midPadding),
        text = reviewsItem.content, textColor = Color.Black, minimizedMaxLines = 4)
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
            )
        }
    }
}