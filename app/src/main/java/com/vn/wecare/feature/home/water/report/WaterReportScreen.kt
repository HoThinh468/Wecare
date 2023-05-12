package com.vn.wecare.feature.home.water.report

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.core.data.Response
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.caloriesFormatWithFloat
import com.vn.wecare.utils.common_composable.BarChartItem
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WaterReportScreen(
    modifier: Modifier = Modifier, onNavigateUp: () -> Unit, viewModel: WaterReportViewModel
) {

    val uiState = viewModel.uiState.collectAsState()

    uiState.value.isLoadingData.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading, color = Blue) {}
            }

            is Response.Error -> {
                onNavigateUp()
                Toast.makeText(
                    LocalContext.current, "Loading data fail!", Toast.LENGTH_SHORT
                ).show()
            }

            is Response.Success -> {
                Toast.makeText(
                    LocalContext.current, "Loading data successfully!", Toast.LENGTH_SHORT
                ).show()
            }

            else -> {/* Do nothing */
            }
        }
    }

    Scaffold(
        backgroundColor = LightBlue,
        topBar = {
            WecareAppBar(
                modifier = modifier,
                onLeadingIconPress = onNavigateUp,
                backgroundColor = LightBlue,
                title = "Water history"
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(midPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (checkInternetConnection(LocalContext.current)) {
                ReportContent(modifier = modifier, viewModel = viewModel)
            } else NoNetWorkConnectionUI(modifier = modifier)
        }
    }
}

@Composable
private fun ReportContent(
    modifier: Modifier, viewModel: WaterReportViewModel
) {
    val uiState = viewModel.uiState.collectAsState()

    WaterBarChartReport(modifier = modifier, viewModel = viewModel)
    Spacer(modifier = modifier.height(midPadding))
    Row(modifier = modifier.fillMaxWidth()) {
        SquareCardIndexInformation(
            modifier = modifier.weight(1f),
            description = "Calories burnt",
            iconRes = R.drawable.ic_fire_calo,
            colorIcon = Red400,
            index = caloriesFormatWithFloat(uiState.value.caloriesBurnt),
            unit = "kcal"
        )
        Spacer(modifier = modifier.width(midPadding))
        SquareCardIndexInformation(
            modifier = modifier.weight(1f),
            description = "Average level",
            iconRes = R.drawable.ic_check_circle,
            colorIcon = MaterialTheme.colors.primary,
            index = "${uiState.value.averageLevel}",
            unit = "%"
        )
    }
}

@Composable
private fun NoNetWorkConnectionUI(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.img_no_internet),
        modifier = modifier.size(180.dp),
        contentDescription = null
    )
    Text(
        text = "No internet connection, please connect to the internet to view report!",
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun WaterBarChartReport(
    modifier: Modifier, viewModel: WaterReportViewModel
) {

    val uiState = viewModel.uiState.collectAsState()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(RoundedCornerShape(mediumRadius))
            .background(MaterialTheme.colors.background), elevation = 20.dp
    ) {
        Column(
            modifier = modifier.padding(horizontal = midPadding, vertical = midPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "${uiState.value.firstDayOfWeek} - ${uiState.value.lastDayOfWeek}",
                    style = MaterialTheme.typography.h4
                )
                Row {
                    IconButton(onClick = { viewModel.onPreviousWeekClick() }) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null,
                            tint = Blue
                        )
                    }
                    IconButton(enabled = uiState.value.isNextClickEnable,
                        onClick = { viewModel.onNextWeekClick() }) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = if (uiState.value.isNextClickEnable) Blue else MaterialTheme.colors.secondary
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(midPadding))
            if (uiState.value.isAbleToShowBarChart) {
                Row(
                    modifier = modifier
                        .weight(11f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (item in uiState.value.dayReportList) {
                        val progress = item.drankAmount.toFloat() / item.targetAmount.toFloat()
                        BarChartItem(
                            itemTitle = item.dayOfWeek,
                            progress = progress,
                            index = item.drankAmount
                        )
                    }
                }
            } else {
                Image(
                    painter = painterResource(id = R.drawable.img_no_water_record),
                    modifier = modifier.size(200.dp),
                    contentDescription = null
                )
                Text(
                    text = "No report for this week!",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = modifier.height(halfMidPadding))
            Row(
                modifier = modifier.weight(2f), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier
                        .size(40.dp)
                        .weight(1f),
                    painter = painterResource(id = R.drawable.ic_water),
                    contentDescription = null,
                    tint = Blue
                )
                Column(
                    modifier = modifier
                        .weight(6f)
                        .padding(horizontal = halfMidPadding)
                ) {
                    Text(text = "Average", style = MaterialTheme.typography.body2)
                    Text(
                        text = "${uiState.value.averageAmount} ml",
                        style = MaterialTheme.typography.h2
                    )
                }
                Image(
                    modifier = modifier
                        .weight(1f)
                        .size(56.dp),
                    painter = painterResource(id = R.drawable.ic_bottle_smile),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun SquareCardIndexInformation(
    modifier: Modifier,
    description: String,
    @DrawableRes iconRes: Int,
    colorIcon: Color,
    index: String,
    unit: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        backgroundColor = LightBlue,
        shape = RoundedCornerShape(mediumRadius),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.secondary)
    ) {
        Column(modifier = modifier.padding(normalPadding)) {
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = modifier.padding(end = smallPadding),
                    text = description,
                    style = MaterialTheme.typography.h5
                )
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = colorIcon
                )
            }
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = OpenSans, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp
                    )
                ) {
                    append("$index ")
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = OpenSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Blue
                    )
                ) {
                    append(unit)
                }
            })
        }
    }
}
