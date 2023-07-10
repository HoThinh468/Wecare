package com.vn.wecare.feature.home.goal.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalDashboardHomeCard(
    modifier: Modifier, onCardClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onCardClick
    ) {
        Column(
            modifier = modifier
                .heightIn()
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Improve mood",
                style = MaterialTheme.typography.h5,
            )
            Spacer(modifier = modifier.height(halfMidPadding))
//            Row(
//                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text(
//                        "From",
//                        style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
//                    )
//                    Text(
//                        "03-07-2023", style = MaterialTheme.typography.body1
//                    )
//                }
//                Column {
//                    Text(
//                        "To",
//                        style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
//                    )
//                    Text(
//                        "03-08-2023", style = MaterialTheme.typography.body1
//                    )
//                }
//            }
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
            Spacer(modifier = modifier.height(smallPadding))
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