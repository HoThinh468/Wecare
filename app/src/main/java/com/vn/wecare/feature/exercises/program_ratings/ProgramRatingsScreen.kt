package com.vn.wecare.feature.exercises.program_ratings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vn.wecare.core.model.ListReviewsItem
import com.vn.wecare.core.model.listReviewitem
import com.vn.wecare.feature.exercises.exercise_list.ExerciseLevel
import com.vn.wecare.feature.exercises.program_detail.ProgramDetailScreen
import com.vn.wecare.feature.exercises.widget.RatingStar
import com.vn.wecare.feature.exercises.widget.ReviewsWidget
import com.vn.wecare.ui.theme.Black900
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.YellowStar
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.iconSize
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.tinyPadding

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    ProgramRatingsScreen(
        onNavigationBack = {},
        title = "High intensity full body workout",
        rating = 4,
        ratedNumber = 231,
        listReviews = listReviewitem
    )
}


@Composable
fun ProgramRatingsScreen(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    title: String,
    rating: Int,
    ratedNumber: Int,
    listReviews: Array<ListReviewsItem> = listReviewitem
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                elevation = 10.dp,
                navigationIcon = {
                    IconButton(onClick = { onNavigationBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = {  /*TODO*/ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = Color.Black
                        )
                    }
                },
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = WeCareTypography.h5,
                        color = Color.Black
                    )
                },
                backgroundColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Column(
                modifier = modifier.padding(horizontal = halfMidPadding, vertical = midPadding)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.padding(start = smallPadding, end = halfMidPadding)
                    ) {
                        Text(
                            modifier = modifier.padding(bottom = tinyPadding),
                            text = "$rating/5",
                            style = WeCareTypography.h2
                        )
                        RatingStar(rating = rating)
                        Text(
//                            modifier = modifier.padding(bottom = tinyPadding),
                            text = "$ratedNumber reviewers",
                            style = WeCareTypography.caption,
                            color = Color.Black
                        )
                    }
                    Box(
                        modifier = modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .background(Grey500)
                    )
                    FilterComponent()
                }

                LazyColumn {
                    items(listReviews) {
                        ReviewsWidget(reviewsItem = it)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterComponent(
    modifier: Modifier = Modifier,
    viewModel: ProgramRatingsViewModel = hiltViewModel()
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(start = smallPadding)
    ) {
        Row(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = smallPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonFilterByStar(
                isAll = true,
                onClick = { viewModel.setFilterStar(0) },
                viewModel = viewModel
            )
            ButtonFilterByStar(
                starNumber = 5,
                onClick = { viewModel.setFilterStar(5) },
                viewModel = viewModel
            )
            ButtonFilterByStar(
                starNumber = 4,
                onClick = { viewModel.setFilterStar(4) },
                viewModel = viewModel
            )
            ButtonFilterByStar(
                starNumber = 3,
                onClick = { viewModel.setFilterStar(3) },
                viewModel = viewModel
            )
        }
        Row(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ButtonFilterByStar(
                starNumber = 2,
                onClick = { viewModel.setFilterStar(2) },
                viewModel = viewModel
            )
            ButtonFilterByStar(
                starNumber = 1,
                onClick = { viewModel.setFilterStar(1) },
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ButtonFilterByStar(
    modifier: Modifier = Modifier,
    starNumber: Int = 0,
    onClick: () -> Unit = {},
    isAll: Boolean = false,
    viewModel: ProgramRatingsViewModel
) {
    val filterStar = viewModel.filterStar.collectAsState().value

    val color = if (filterStar == starNumber) Color.Black else Color.White

    Button(
        modifier = modifier
            .width(60.dp)
            .height(30.dp),
        shape =
        RoundedCornerShape(60.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isAll) {
                Text(
                    modifier = modifier.padding(end = tinyPadding),
                    text = "ALL",
                    style = WeCareTypography.caption,
                    color = if (filterStar == starNumber) {
                        Color.White
                    } else {
                        Color.Black
                    },
                )
            } else {
                Text(
                    modifier = modifier.padding(end = tinyPadding),
                    text = "$starNumber",
                    style = WeCareTypography.caption,
                    color = if (filterStar == starNumber) {
                        Color.White
                    } else {
                        Color.Black
                    },
                )
                Icon(
                    imageVector = Icons.Filled.Star, contentDescription = "",
                    tint = if (filterStar == starNumber) {
                        Color.White
                    } else {
                        Color.Black
                    },
                    modifier = modifier.size(
                        midPadding
                    )
                )
            }
        }

    }
}