package com.vn.wecare.feature.home.step_count.ui.compose

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StepCountHomeCard(
    modifier: Modifier = Modifier, onCardClick: () -> Unit, steps: Int, calories: Int, time: Int
) {
    Card(
        modifier = modifier
            .padding(end = midPadding)
            .fillMaxWidth(0.48f)
            .height(210.dp),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onCardClick
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(halfMidPadding),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = "Daily activity",
                style = MaterialTheme.typography.h5,
            )
            FootstepCountOverviewItem(
                iconRes = R.drawable.burn,
                index = calories,
                unitRes = "cal",
                modifier = modifier
            )
            FootstepCountOverviewItem(
                iconRes = R.drawable.step,
                index = steps,
                unitRes = "steps",
                modifier = modifier
            )
            FootstepCountOverviewItem(
                iconRes = R.drawable.time,
                index = time,
                unitRes = "min",
                modifier = modifier
            )
        }
    }
}

@Composable
fun FootstepCountOverviewItem(
    @DrawableRes iconRes: Int,
    unitRes: String,
    index: Int,
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = modifier.width(48.dp)) {
            Icon(
                modifier = modifier
                    .padding(vertical = 4.dp, horizontal = halfMidPadding)
                    .size(mediumIconSize),
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = OpenSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                ) {
                    append("$index ")
                }

                withStyle(
                    style = SpanStyle(
                        fontFamily = OpenSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.Black450)
                    )
                ) {
                    if(index >= 10000) {
                        append("\n$unitRes")
                    } else {
                        append(unitRes)
                    }
                }
            }
        )
    }
}