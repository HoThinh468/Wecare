package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.compose.foundation.Image
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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat
import com.vn.wecare.ui.theme.OpenSans
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.iconSize
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.WecareUserConstantValues

@Composable
fun OverviewSection(
    modifier: Modifier,
    totalRecords: Int,
    progress: Float,
    goalStatus: GoalStatus,
    dateSetGoal: Long,
    dateEndGoal: Long,
    weeklyGoalWeight: Float,
    bmr: Int
) {

    val openAnswerDialog = remember {
        mutableStateOf(false)
    }

    if (openAnswerDialog.value) {
        BMRExplanationDialog(modifier = modifier) {
            openAnswerDialog.value = false
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(midPadding)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Total records", style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 32.sp,
                                    fontFamily = OpenSans,
                                    color = MaterialTheme.colors.primary
                                )
                            ) {
                                append(totalRecords.toString())
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp,
                                    fontFamily = OpenSans
                                )
                            ) {
                                append("/7 days")
                            }
                        },
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .clip(Shapes.large)
                        .background(goalStatus.color),
                ) {
                    Text(
                        modifier = modifier.padding(smallPadding),
                        text = goalStatus.value,
                        style = MaterialTheme.typography.button.copy(MaterialTheme.colors.onPrimary)
                    )
                }
            }
            Spacer(modifier = modifier.height(smallPadding))
            LinearProgressIndicator(
                modifier = modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colors.primary,
                strokeCap = StrokeCap.Round,
                progress = progress
            )
            Spacer(modifier = modifier.height(halfMidPadding))
            Row(
                modifier = modifier
                    .width(IntrinsicSize.Max)
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "From:", style = MaterialTheme.typography.caption.copy(
                            color = colorResource(
                                id = R.color.Black450
                            )
                        )
                    )
                    Text(
                        getDayFromLongWithFormat(dateSetGoal),
                        style = MaterialTheme.typography.body1
                    )
                }
                Divider(
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(horizontal = normalPadding)
                        .width(1.dp)
                )
                Column {
                    Text(
                        "To:", style = MaterialTheme.typography.caption.copy(
                            color = colorResource(
                                id = R.color.Black450
                            )
                        )
                    )
                    Text(
                        getDayFromLongWithFormat(dateEndGoal),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            Spacer(modifier = modifier.height(smallPadding))
            ReportDetailItem(
                modifier = modifier,
                title = "Weekly goal weight",
                index = "$weeklyGoalWeight kg",
            )
            ReportDetailItem(
                modifier = modifier, title = "Your BMR", index = "$bmr cal"
            )
            Row(
                modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    openAnswerDialog.value = true
                }) {
                    Icon(
                        modifier = modifier.size(iconSize),
                        imageVector = Icons.Default.Help,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(text = "What is BMR?", style = MaterialTheme.typography.body2)
            }
        }
    }
}


@Composable
private fun BMRExplanationDialog(modifier: Modifier, onDismissDialog: () -> Unit) {
    Dialog(onDismissRequest = { onDismissDialog() }) {
        Card(
            shape = Shapes.large
        ) {
            Column(
                modifier = modifier.padding(midPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = modifier
                        .size(150.dp)
                        .padding(bottom = midPadding),
                    painter = painterResource(id = R.drawable.img_friends),
                    contentDescription = null
                )
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = "Basal metabolic rate (BMR) measures the calories needed to perform your body's most basic (basal) functions, like breathing, circulation, and cell production. BMR is most accurately measured in a lab setting under very restrictive conditions.",
                    style = MaterialTheme.typography.body2,
                )
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = "Basal metabolic rate (BMR) measures the calories needed to perform your body's most basic (basal) functions, like breathing, circulation, and cell production. BMR is most accurately measured in a lab setting under very restrictive conditions.",
                    style = MaterialTheme.typography.body2,
                )
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = "You can measure your BMR by following The Harris-Benedict Equation:\n" + "Men: BMR = 88.362 + (13.397 x weight in kg) + (4.799 x height in cm) - (5.677 x age in years) \n" + "Women: BMR = 447.593 + (9.247 x weight in kg) + (3.098 x height in cm) - (4.330 x age in years)",
                    style = MaterialTheme.typography.body2,
                )
                Spacer(modifier = modifier.height(midPadding))
                Button(modifier = modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    onClick = { onDismissDialog() }) {
                    Text("I UNDERSTAND", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}