package com.vn.wecare.feature.home.water.tracker.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.water.tracker.WaterViewModel
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.WecareAppBar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WaterScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    moveToReportScreen: () -> Unit,
    viewModel: WaterViewModel
) {

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        backgroundColor = LightBlue,
        topBar = {
            WecareAppBar(
                modifier = modifier,
                onLeadingIconPress = onNavigateUp,
                trailingIconRes = R.drawable.ic_bar_chart,
                onTrailingIconPress = moveToReportScreen,
                title = stringResource(id = R.string.water),
                backgroundColor = LightBlue,
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(smallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WaterOverView(
                modifier = modifier,
                currentIndex = uiState.value.currentIndex,
                targetAmount = uiState.value.targetAmount,
                onDrinkClick = viewModel::onDrinkClick,
                progress = uiState.value.progress
            )
            Spacer(modifier = modifier.height(halfMidPadding))
            WaterOpacityPicker(
                modifier = modifier,
                numberOfPage = viewModel.getWaterOpacityNumber(),
                desiredAmount = viewModel.getWaterDrinkingAmount(),
                currentIndex = uiState.value.desiredDrinkingAmountPageIndex,
                onNextClick = viewModel::onNextAmountClick,
                onPreviousClick = viewModel::onPreviousAmountClick,
            )
            Spacer(modifier = modifier.height(midPadding))
            WaterTodayRecords(
                modifier = modifier,
                recordList = uiState.value.recordList,
                onDeleteClick = viewModel::onDeleteRecordClick,
                waterViewModel = viewModel
            )
        }
    }
}

@Composable
fun WaterOverView(
    modifier: Modifier,
    currentIndex: Int,
    targetAmount: Int,
    onDrinkClick: () -> Unit,
    progress: Float
) {

    val progressAnimationValue by animateFloatAsState(
        targetValue = progress, animationSpec = tween(1000)
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = modifier
                .size(230.dp)
                .border(width = 2.dp, shape = CircleShape, color = Color(0xFFB7DCF0))
        ) {}
        CircularProgressIndicator(
            modifier = modifier.size(200.dp),
            progress = 1f,
            color = Blue.copy(alpha = 0.3f),
            strokeWidth = 12.dp
        )
        CircularProgressIndicator(
            modifier = modifier.size(200.dp),
            progress = progressAnimationValue,
            color = Blue,
            strokeWidth = 12.dp,
            strokeCap = StrokeCap.Round
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$currentIndex ml", style = MaterialTheme.typography.h1)
            Text(text = "Target: $targetAmount ml", style = MaterialTheme.typography.body2)
        }
        Box(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(LightBlue)
                .align(Alignment.TopStart), contentAlignment = Alignment.Center
        ) {
            Text(
                "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.h4.copy(color = Blue)
            )
        }
        Box(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFFDFECF7))
                .align(Alignment.BottomEnd)
                .clickable {
                    onDrinkClick()
                }, contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_cup),
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WaterOpacityPicker(
    modifier: Modifier,
    numberOfPage: Int,
    desiredAmount: Int,
    currentIndex: Int,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {
                onPreviousClick()
                coroutineScope.launch {
                    pagerState.animateScrollToPage(currentIndex)
                }
            }, modifier = modifier.weight(1f), enabled = currentIndex > 0
        ) {
            Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
        }
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .weight(2f)
                .wrapContentWidth(),
            pageCount = numberOfPage,
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = false
        ) {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(mediumRadius))
                    .background(Blue)
            ) {
                Text(
                    "$desiredAmount ml",
                    modifier = modifier.padding(
                        vertical = smallPadding, horizontal = halfMidPadding
                    ),
                    style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onPrimary),
                )
            }
        }
        IconButton(
            onClick = {
                onNextClick()
                coroutineScope.launch {
                    pagerState.animateScrollToPage(currentIndex)
                }
            }, modifier = modifier.weight(1f), enabled = currentIndex < numberOfPage - 1
        ) {
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        }
    }
}

@Composable
fun WaterTodayRecords(
    modifier: Modifier,
    recordList: List<WaterRecordEntity>,
    onDeleteClick: (record: WaterRecordEntity) -> Unit,
    waterViewModel: WaterViewModel
) {
    Text("Today's records", style = MaterialTheme.typography.h4)
    if (recordList.isEmpty()) {
        Image(
            painter = painterResource(id = R.drawable.img_no_water_record),
            modifier = modifier
                .padding(top = mediumPadding, bottom = halfMidPadding)
                .size(160.dp),
            contentDescription = null
        )
        Text(
            text = "No records yet, drink now to see your record!",
            style = MaterialTheme.typography.body2
        )
    } else {
        LazyColumn {
            items(recordList.size) { i ->
                WaterRecordItem(
                    modifier = modifier,
                    record = recordList[i],
                    onDeleteClick = onDeleteClick,
                    waterViewModel = waterViewModel
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
private fun WaterRecordItem(
    modifier: Modifier,
    record: WaterRecordEntity,
    onDeleteClick: (record: WaterRecordEntity) -> Unit,
    waterViewModel: WaterViewModel
) {

    val simpleDateFormat = SimpleDateFormat("hh:mm aa")
    var mExpanded by remember { mutableStateOf(false) }
    var openUpdateDialog by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.padding(smallPadding), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = modifier.weight(1f),
            painter = painterResource(id = R.drawable.ic_water),
            contentDescription = null,
            tint = Blue
        )
        Column(
            modifier = modifier
                .weight(6f)
                .padding(horizontal = smallPadding)
        ) {
            Text(text = "${record.amount} ml", style = MaterialTheme.typography.body1)
            Text(
                text = simpleDateFormat.format(record.dateTime.time),
                style = MaterialTheme.typography.caption
            )
        }
        IconButton(onClick = { mExpanded = true }) {
            Icon(
                modifier = modifier.weight(1f),
                imageVector = Icons.Default.MoreVert,
                contentDescription = null
            )
            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = modifier.background(LightBlue)
            ) {
                DropdownMenuItem(onClick = {
                    openUpdateDialog = true
                    mExpanded = false
                }) {
                    Text(text = "Edit")
                }
                DropdownMenuItem(onClick = {
                    onDeleteClick(record)
                    mExpanded = false
                }) {
                    Text(text = "Delete")
                }
            }
        }
    }
    Divider()
    if (openUpdateDialog) {
        UpdateWaterRecordAmountDialog(
            modifier = modifier,
            onCloseClick = { openUpdateDialog = false },
            record = record,
            viewModel = waterViewModel
        )
    }
}