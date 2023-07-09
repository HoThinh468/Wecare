package com.vn.wecare.feature.home.bmi.ui

import android.os.Build
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.ThresholdConfig
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.ext.convertBMIToString
import com.vn.wecare.core.ext.toDateAndTime
import com.vn.wecare.core.ext.toGender
import com.vn.wecare.feature.home.bmi.model.BMIHistoryEntity
import com.vn.wecare.feature.home.bmi.viewmodel.BMIHistoryViewModel
import com.vn.wecare.feature.home.bmi.viewmodel.BMIViewModel
import com.vn.wecare.feature.training.dashboard.widget.ProgressBar
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.WeCareTypography

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun BMIHistoryScreen(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit = {},
    viewModel: BMIHistoryViewModel = hiltViewModel(),
    bmiViewModel: BMIViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { onNavigationBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                title = {
                    Text(
                        "BMI History",
                        style = WeCareTypography.h4,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                },
                backgroundColor = Color(0xFFF6FBF6)
            )
        }
    ) { padding ->

        when (val response = viewModel.response) {
            is Response.Loading -> Log.e("Update BMI", "Loading")
            is Response.Success -> Log.e("Update BMI", "Success")
            is Response.Error -> Log.e("Update BMI", "Error: ${response.e?.message}")
        }
        var openUpdateWeightDialog by remember { mutableStateOf(false) }
        var currentItemIndex by remember { mutableStateOf(0) }

        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        when (val listHistoryResponse = viewModel.listHistoryResponse) {
            is Response.Loading -> ProgressBar()
            is Response.Error -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = modifier.size(250.dp),
                        painter = rememberAsyncImagePainter(
                            R.drawable.no_infor, imageLoader
                        ),
                        contentDescription = "",
                    )
                }
            }

            is Response.Success -> {
                Column(
                    modifier = modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(Color(0xFFF6FBF6))
                ) {
                    Text(
                        "Showing ${viewModel.listHistory.collectAsState().value.size} report(s) below:",
                        style = WeCareTypography.body1.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = modifier.padding(start = 16.dp, bottom = 24.dp),
                        textAlign = TextAlign.Start,
                        color = Color.Black
                    )
                    if (listHistoryResponse.data.isEmpty()) {
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = modifier.size(250.dp),
                                painter = rememberAsyncImagePainter(
                                    R.drawable.no_infor, imageLoader
                                ),
                                contentDescription = "",
                            )
                        }
                    }
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                    ) {
                        items(
                            items = viewModel.listHistory.value,
                            key = { item -> item.time }
                        ) { item ->
                            val currentItem by rememberUpdatedState(item)
                            val dismissState = rememberDismissState(
                                confirmStateChange = {
                                    if (it == DismissValue.DismissedToStart) {
                                        viewModel.removeBMIHistory(
                                            viewModel.listHistory.value.indexOf(
                                                currentItem
                                            )
                                        )
                                    } else {
                                        openUpdateWeightDialog = true
                                        currentItemIndex = viewModel.listHistory.value.indexOf(
                                            currentItem
                                        )
                                    }
                                    true
                                }
                            )
                            SwipeToDismiss(
                                state = dismissState,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .animateItemPlacement(),
                                directions = setOf(
                                    DismissDirection.EndToStart,
                                    DismissDirection.StartToEnd
                                ),
                                dismissThresholds = { FractionalThreshold(0.5f) },
                                background = {
                                    SwipeBackground(dismissState)
                                },
                                dismissContent = {
                                    BMIHistoryItem(
                                        uiState = item
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }


        if (openUpdateWeightDialog) {
            UpdateBMIHistoryDialog(
                modifier = modifier,
                onCloseClick = { openUpdateWeightDialog = false },
                uiState = viewModel.listHistory.collectAsState().value[currentItemIndex],
                updateHistory = {
                    viewModel.updateBMIHistory(
                        currentItemIndex,
                        it
                    )
                    bmiViewModel.updateUserHeight(it.height.toString())
                    bmiViewModel.updateUserWeight(it.weight.toString())
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun SwipeBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection ?: return

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.LightGray
            DismissValue.DismissedToEnd -> Color(0xFF589BFF)
            DismissValue.DismissedToStart -> Color(0xFFCD1212)
        }
    )
    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }
    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Edit
        DismissDirection.EndToStart -> Icons.Default.Delete
    }
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.7f else 1f
    )

    Box(
        Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxSize()
            .background(color),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = "Localized description",
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .scale(scale)
                .size(32.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}


@Composable
fun BMIHistoryItem(
    modifier: Modifier = Modifier,
    uiState: BMIHistoryEntity
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                textAlign = TextAlign.Start,
                modifier = modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                text = uiState.time.toDateAndTime(),
                style = WeCareTypography.body1.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Row(
                modifier = modifier
                    .padding(start = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalText(
                    modifier.weight(3f),
                    text1 = convertBMIToString(uiState.height, uiState.weight),
                    text2 = "BMI",
                    isBMI = true
                )
                Row(
                    modifier = modifier
                        .weight(7f)
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalText(
                        text1 = uiState.age.toString(),
                        text2 = "Age"
                    )
                    HorizontalText(
                        text1 = uiState.gender.toGender(),
                        text2 = "Gender"
                    )
                    HorizontalText(
                        text1 = uiState.weight.toString(),
                        text2 = "Weight"
                    )
                    HorizontalText(
                        text1 = uiState.height.toString(),
                        text2 = "Height"
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalText(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String,
    isBMI: Boolean = false,
) {
    Column(
        modifier = modifier.padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = text1,
            style = WeCareTypography.body1.copy(
                fontSize = if (isBMI) 32.sp else 16.sp,
                color = if (isBMI) Green500 else Color.Black,
                fontWeight = if (isBMI) FontWeight(600) else FontWeight(400)
            )
        )
        Text(
            textAlign = TextAlign.Center,
            text = text2,
            style = WeCareTypography.body1.copy(
                fontSize = 10.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight(275),
                color = Color(0xFF999999),
            )
        )
    }
}