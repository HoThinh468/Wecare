package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat2
import com.vn.wecare.ui.theme.OpenSans
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.WecareAppBar
import com.vn.wecare.utils.weightFormat

@Composable
fun WeeklyRecordAppBar(
    modifier: Modifier, uiState: WeekRecordDetailUiState, navigateBack: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        WecareAppBar(
            modifier = modifier,
            onLeadingIconPress = navigateBack,
            title = "${uiState.startDay} - ${uiState.endDay}"
        )
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = normalPadding),
            text = "Total records",
            style = MaterialTheme.typography.body1
        )
        Text(
            modifier = modifier.padding(horizontal = normalPadding),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        fontFamily = OpenSans,
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append(uiState.totalRecords.toString())
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = OpenSans
                    )
                ) {
                    append("/7 days")
                }
            },
        )
        Spacer(modifier = modifier.height(smallPadding))
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = normalPadding)
                .height(8.dp),
            color = MaterialTheme.colors.primary,
            strokeCap = StrokeCap.Round,
            progress = uiState.progress
        )
        Spacer(modifier = modifier.height(halfMidPadding))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = mediumPadding, vertical = smallPadding)
                .height(IntrinsicSize.Min), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "${uiState.caloriesIn} cal", style = MaterialTheme.typography.h5)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropUp,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                    Text(
                        text = "Calories in",
                        style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450))
                    )
                }
            }
            Divider(
                modifier = modifier
                    .fillMaxHeight()
                    .padding(horizontal = midPadding)
                    .width(1.dp)
            )
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${uiState.caloriesOut} cal", style = MaterialTheme.typography.h5)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Red400
                    )
                    Text(
                        text = "Calories out",
                        style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(smallPadding))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = normalPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Calories difference:", style = MaterialTheme.typography.button)
            Text(
                text = "${uiState.caloriesDifference} cal", style = MaterialTheme.typography.button
            )
        }
        Spacer(modifier = modifier.height(smallPadding))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = normalPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Weight difference assumed:", style = MaterialTheme.typography.button)
            Text(
                text = "${uiState.weightDifference.weightFormat()} kg",
                style = MaterialTheme.typography.button
            )
        }
    }
}