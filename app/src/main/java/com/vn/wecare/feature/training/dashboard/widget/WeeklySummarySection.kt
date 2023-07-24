package com.vn.wecare.feature.training.dashboard.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vn.wecare.feature.training.dashboard.TrainingViewModel
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.training.utils.secondToHourUtil
import com.vn.wecare.feature.training.utils.stringWith2Decimals
import com.vn.wecare.ui.theme.Black900
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.utils.getFirstWeekdayTimestamp
import com.vn.wecare.utils.getMondayOfTimestampWeek
import java.util.Calendar


@Composable
fun WeeklyCheck(
    viewModel: TrainingViewModel = hiltViewModel(),
    weeklyContents: @Composable (weeklyCheck: List<Int>) -> Unit
) {
    when (val weeklyCheck = viewModel.weeklyCheckResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> weeklyContents(weeklyCheck.data)
        is Response.Error -> print(weeklyCheck.e)
    }
}

@Composable
fun CheckingWeeklySummarySection(
    modifier: Modifier,
    duration: Int,
    kcal: Double,
    session: Int
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = 4.dp
            )
            .height(210.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 16.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier.padding(top = 16.dp),
                text = "This Week",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Black900
            )
            WeeklyCheck(
                weeklyContents = { checks ->
                    TrainingCalendar(
                        modifier = modifier,
                        checks = checks,
                        startDate = getMondayOfTimestampWeek(System.currentTimeMillis()).dayOfMonth + 1,
                        endDate = getMondayOfTimestampWeek(System.currentTimeMillis()).dayOfMonth + 7
                    )
                }
            )
            Box(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(Grey500)
            )
            SummarySection(modifier = modifier, kcal = kcal, session = session, duration = duration)
        }
    }
}

@Composable
fun CalendarDayItem(
    modifier: Modifier,
    date: Int,
) {
    Card(
        modifier = modifier
            .width(30.dp)
            .height(50.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White,
        content = {
            Text(
                modifier = modifier
                    .fillMaxHeight()
                    .wrapContentHeight(
                        align = Alignment.CenterVertically
                    ),
                textAlign = TextAlign.Center,
                text = "$date",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    )
}

@Composable
fun CalendarTrainedDayItem(
    modifier: Modifier,
    date: Int,
) {
    Card(
        modifier = modifier
            .width(30.dp)
            .height(50.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Green500,
        content = {
            Text(
                modifier = modifier
                    .fillMaxHeight()
                    .wrapContentHeight(
                        align = Alignment.CenterVertically
                    ),
                textAlign = TextAlign.Center,
                text = "$date",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    )
}

@Composable
fun TrainingCalendar(
    modifier: Modifier,
    checks: List<Int>,
    startDate: Int,
    endDate: Int
) {
    Box() {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp, 8.dp, 8.dp, 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            for (i in startDate..endDate) {
                if(checks.contains(i+1)) {
                    CalendarTrainedDayItem(modifier = modifier, date = i+1 )
                } else {
                    CalendarDayItem(modifier = modifier, date = i+1)
                }
            }
        }
    }
}

@Composable
fun SummarySection(
    modifier: Modifier,
    duration: Int,
    kcal: Double,
    session: Int
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = secondToHourUtil(duration),
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextWithBoldNumber(modifier = modifier, numberDouble = kcal, suffix = " Calorie")
            TextWithBoldNumber(modifier = modifier, numberInt = session, suffix = " session")
        }
    }
}

@Composable
fun TextWithBoldNumber(
    modifier: Modifier,
    numberDouble: Double? = null,
    numberInt: Int? = null,
    suffix: String
) {
    Text(
        modifier = modifier.padding(8.dp),
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.primary
                )
            ) {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    if (numberInt != null)
                        append(numberInt.toString())
                    else
                        append(stringWith2Decimals(numberDouble!!))
                }
                withStyle(
                    style = SpanStyle(
                        color = Black900
                    )
                ) {
                    append(pluralOrNot(numberDouble ?: numberInt!!.toDouble(), suffix))
                }
            }
        }
    )
}

fun pluralOrNot(
    number: Double,
    suffix: String
): String = if (number != 1.0) "${suffix}s"
else suffix