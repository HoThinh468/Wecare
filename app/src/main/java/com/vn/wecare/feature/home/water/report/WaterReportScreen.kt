package com.vn.wecare.feature.home.water.report

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.BarChartItem
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WaterReportScreen(
    modifier: Modifier = Modifier, onNavigateUp: () -> Unit
) {
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
            WaterBarChartReport(modifier = modifier)
            Spacer(modifier = modifier.height(midPadding))
            Row(modifier = modifier.fillMaxWidth()) {
                SquareCardIndexInformation(
                    modifier = modifier.weight(1f),
                    description = "Calories burnt",
                    iconRes = R.drawable.ic_fire_calo,
                    colorIcon = Red400,
                    index = "1234",
                    unit = "kcal"
                )
                Spacer(modifier = modifier.width(midPadding))
                SquareCardIndexInformation(
                    modifier = modifier.weight(1f),
                    description = "Average level",
                    iconRes = R.drawable.ic_check_circle,
                    colorIcon = MaterialTheme.colors.primary,
                    index = "80",
                    unit = "%"
                )
            }
        }
    }
}

@Composable
private fun WaterBarChartReport(
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp)
            .clip(RoundedCornerShape(mediumRadius))
            .background(MaterialTheme.colors.background), elevation = 20.dp
    ) {
        Column(
            modifier = modifier.padding(horizontal = midPadding, vertical = midPadding)
        ) {
            Row(
                modifier = modifier.weight(1f).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("30 Apr - 04 May, 2023", style = MaterialTheme.typography.h4)
                Row() {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null,
                            tint = Blue
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(midPadding))
            Row(
                modifier = modifier
                    .weight(8f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BarChartItem(itemTitle = "Mon", progress = 1f)
                BarChartItem(itemTitle = "Tue", progress = 0.8f)
                BarChartItem(itemTitle = "Wed", progress = 0.5f)
                BarChartItem(itemTitle = "Thu", progress = 1f)
                BarChartItem(itemTitle = "Fri", progress = 0.7f)
                BarChartItem(itemTitle = "Sat", progress = 0.2f)
                BarChartItem(itemTitle = "Sun", progress = 0f)
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
                    Text(text = "2000 ml", style = MaterialTheme.typography.h2)
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
