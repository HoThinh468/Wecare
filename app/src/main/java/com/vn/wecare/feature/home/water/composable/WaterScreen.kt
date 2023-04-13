package com.vn.wecare.feature.home.water.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
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

private const val WATER_OPACITY_PAGE_COUNT = 7

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
            WaterOpacityPicker(modifier = modifier)
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
        contentAlignment = Alignment.Center, modifier = modifier.padding(midPadding)
    ) {
        Canvas(
            modifier = modifier
                .size(260.dp)
                .border(width = 2.dp, shape = CircleShape, color = Color(0xFFB7DCF0))
        ) {}
        CircularProgressIndicator(
            modifier = modifier.size(220.dp),
            progress = 1f,
            color = Blue.copy(alpha = 0.3f),
            strokeWidth = 16.dp
        )
        CircularProgressIndicator(
            modifier = modifier.size(220.dp), progress = 0.75f, color = Blue, strokeWidth = 16.dp
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "1200ml", style = MaterialTheme.typography.h1)
            Text(text = "Target: 2500ml", style = MaterialTheme.typography.body2)
        }
        Box(
            modifier = modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF88CAEF))
                .align(Alignment.TopStart), contentAlignment = Alignment.Center
        ) {
            Text(
                "50%",
                style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onPrimary)
            )
        }
        Box(
            modifier = modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(LightBlue)
                .align(Alignment.BottomEnd)
                .clickable { }, contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = modifier.size(40.dp),
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
        modifier = modifier.fillMaxWidth(),
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
            modifier = modifier.weight(2f).wrapContentWidth(),
            pageCount = WATER_OPACITY_PAGE_COUNT,
        ) {
            Text(
                "Page: $it",
                style = MaterialTheme.typography.button,
            )
        }
        IconButton(
            onClick = { /*TODO*/ }, modifier = modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        }
    }
}