package com.vn.wecare.feature.home.water.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CardListTile

private const val WATER_OPACITY_PAGE_COUNT = 7
private val waterOpacityList = listOf(100, 200, 300, 400, 500, 600, 700)

@Preview
@Composable
fun WaterScreenPreview() {
    WaterScreen {}
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WaterScreen(
    modifier: Modifier = Modifier, onNavigateUp: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = LightBlue,
        topBar = {
            WaterAppBar(modifier = modifier) { onNavigateUp() }
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(smallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WaterOverView(modifier = modifier)
            Spacer(modifier = modifier.height(halfMidPadding))
            WaterOpacityPicker(modifier = modifier)
            Spacer(modifier = modifier.height(halfMidPadding))
            SetWaterTarget(modifier = modifier)
            Spacer(modifier = modifier.height(halfMidPadding))
            WaterTodayRecords(modifier = modifier)
        }
    }
}

@Composable
fun WaterAppBar(
    modifier: Modifier,
    navigateUp: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = tinyPadding)
            .background(color = LightBlue),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = navigateUp) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = null
            )
        }
        Text(text = stringResource(id = R.string.water), style = MaterialTheme.typography.h4)
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bar_chart), contentDescription = null
            )
        }
    }
}

@Composable
fun WaterOverView(
    modifier: Modifier
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = modifier
                .size(220.dp)
                .border(width = 2.dp, shape = CircleShape, color = Color(0xFFB7DCF0))
        ) {}
        CircularProgressIndicator(
            modifier = modifier.size(180.dp),
            progress = 1f,
            color = Blue.copy(alpha = 0.3f),
            strokeWidth = 16.dp
        )
        CircularProgressIndicator(
            modifier = modifier.size(180.dp), progress = 0.75f, color = Blue, strokeWidth = 16.dp
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "1200ml", style = MaterialTheme.typography.h2)
            Text(text = "Target: 2500ml", style = MaterialTheme.typography.caption)
        }
        Box(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(LightBlue)
                .align(Alignment.TopStart), contentAlignment = Alignment.Center
        ) {
            Text(
                "100%", style = MaterialTheme.typography.h4.copy(color = Blue)
            )
        }
        Box(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFFDFECF7))
                .align(Alignment.BottomEnd)
                .clickable { }, contentAlignment = Alignment.Center
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
    modifier: Modifier
) {

    val pageState = rememberPagerState(initialPage = 3)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = { /*TODO*/ }, modifier = modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
        }
        HorizontalPager(
            state = pageState,
            modifier = modifier
                .weight(4f)
                .wrapContentWidth(),
            pageCount = WATER_OPACITY_PAGE_COUNT,
            pageSpacing = smallPadding,
            pageSize = PageSize.Fixed(80.dp),
            userScrollEnabled = false,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (it == 3) {
                Box(
                    modifier = modifier
                        .clip(RoundedCornerShape(mediumRadius))
                        .background(Blue)
                ) {
                    Text(
                        "${waterOpacityList[it]}ml",
                        modifier = modifier.padding(
                            vertical = smallPadding, horizontal = halfMidPadding
                        ),
                        style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onPrimary),
                    )
                }
            } else {
                Text(
                    "${waterOpacityList[it]}ml",
                    style = MaterialTheme.typography.h4.copy(
                        color = MaterialTheme.colors.onSecondary.copy(alpha = 0.4f)
                    ),
                )
            }
        }
        IconButton(
            onClick = { /*TODO*/ }, modifier = modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        }
    }
}

@Composable
fun SetWaterTarget(modifier: Modifier) {
    CardListTile(
        modifier = modifier,
        leadingIconRes = R.drawable.ic_fact_check,
        trailingIconRes = R.drawable.ic_arrow_forward,
        titleRes = R.string.set_water_target,
        subTitle = "You should drink at least 40ml of water per kg of your body weight a day (2520ml)",
        colorIconRes = R.color.Red400,
        elevation = null,
        backgroundColor = LightBlue
    )
}

@Composable
fun WaterTodayRecords(
    modifier: Modifier
) {
    Text("Today's records", style = MaterialTheme.typography.h4)
    Spacer(modifier = modifier.height(halfMidPadding))
    LazyColumn {
        items(count = 2) { record ->
            WaterRecordItem(modifier = modifier)
            WaterRecordItem(modifier = modifier)
            WaterRecordItem(modifier = modifier)
            WaterRecordItem(modifier = modifier)
        }
    }
}

@Composable
private fun WaterRecordItem(
    modifier: Modifier,
    waterAmount: Int = 250,
    time: String = "09:30 AM",
) {
    Row(
        modifier = modifier.padding(smallPadding), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = modifier.weight(1f),
            painter = painterResource(id = R.drawable.ic_water),
            contentDescription = null,
            tint = Blue
        )
        Column(modifier = modifier.weight(6f).padding(horizontal = smallPadding)) {
            Text(text = "${waterAmount}ml", style = MaterialTheme.typography.body1)
            Text(text = time, style = MaterialTheme.typography.caption)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = modifier.weight(1f),
                imageVector = Icons.Default.MoreVert,
                contentDescription = null
            )
        }
    }
    Divider()
}