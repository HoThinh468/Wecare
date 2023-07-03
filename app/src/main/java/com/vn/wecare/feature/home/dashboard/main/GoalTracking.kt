package com.vn.wecare.feature.home.dashboard.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.OpenSans
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalTracking(
    modifier: Modifier
) {
    Card(modifier = modifier.fillMaxWidth(), shape = Shapes.small, onClick = {}) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Text(
                "Goal tracker", style = MaterialTheme.typography.h5
            )
            Text(
                "Your goal is:",
                style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
            )
            Text(
                "Gain muscle", style = MaterialTheme.typography.h2
            )
            Spacer(modifier = modifier.height(halfMidPadding))
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "Day set goal:",
                        style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
                    )
                    Text(
                        "03-07-2023", style = MaterialTheme.typography.body1
                    )
                }
                Column {
                    Text(
                        "Day end goal:",
                        style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
                    )
                    Text(
                        "03-08-2023", style = MaterialTheme.typography.body1
                    )
                }
            }
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold, fontSize = 32.sp, fontFamily = OpenSans
                    )
                ) {
                    append("30")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Normal, fontSize = 16.sp, fontFamily = OpenSans
                    )
                ) {
                    append(" days left")
                }
            })
            LinearProgressIndicator(
                modifier = modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = MaterialTheme.colors.primary,
                strokeCap = StrokeCap.Round,
                progress = 0.5f
            )
        }
    }
}