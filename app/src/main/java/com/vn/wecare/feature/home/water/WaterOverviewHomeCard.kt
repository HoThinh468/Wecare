package com.vn.wecare.feature.home.water

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WaterOverviewHomeCard(
    modifier: Modifier,
    onCardClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = midPadding),
        elevation = smallElevation,
        onClick = onCardClick,
        shape = Shapes.small,
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
                    text = stringResource(id = R.string.water),
                    style = MaterialTheme.typography.h4,
                )
                Text(
                    modifier = modifier.padding(top = smallPadding),
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = colorResource(id = R.color.Blue400),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 32.sp,
                                fontFamily = OpenSans
                            )
                        ) {
                            append("1600")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = colorResource(id = R.color.Black450),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                fontFamily = OpenSans
                            )
                        ) {
                            append("/2000 ml")
                        }
                    }
                )
            }
            Row {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_remove),
                        contentDescription = null,
                        tint = colorResource(id = R.color.Blue400)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = colorResource(id = R.color.Blue400)
                    )
                }
            }
        }
    }
}