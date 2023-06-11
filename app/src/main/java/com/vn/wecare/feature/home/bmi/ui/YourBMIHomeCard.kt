package com.vn.wecare.feature.home.bmi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.bmiFormatWithFloat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun YourBMIHomeCard(
    modifier: Modifier,
    bmiIndex: Float,
    onCardClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = normalPadding),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onCardClick
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "BMI",
                    style = MaterialTheme.typography.h5,
                )
                Text(
                    modifier = modifier.padding(top = smallPadding),
                    text = bmiFormatWithFloat(bmiIndex),
                    style = MaterialTheme.typography.h1.copy(
                        fontSize = 40.sp,
                        color = if (bmiIndex in 18.5..24.9) getHappyMoodColor(
                            bmiIndex
                        ) else getBadMoodColor(bmiIndex)
                    )
                )
            }
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_happy),
                    contentDescription = null,
                    tint = getHappyMoodColor(bmiIndex)
                )
                Spacer(modifier = modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_sad),
                    contentDescription = null,
                    tint = getBadMoodColor(bmiIndex)
                )
            }
        }
    }
}

private fun getHappyMoodColor(bmi: Float): Color {
    return if (bmi in 18.5..24.9) Green500 else Grey500
}

private fun getBadMoodColor(bmi: Float): Color {
    return if (bmi <= 18.5) Blue
    else if (bmi in 25.0..29.9) Yellow
    else if (bmi > 30) Red400
    else Grey500
}