package com.vn.wecare.feature.training.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.vn.wecare.feature.training.utils.secondToHourUtil
import com.vn.wecare.feature.training.utils.stringWith2Decimals
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500

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
                .fillMaxSize()
        ) {
            Text(
                modifier = modifier.padding(top = 16.dp, start = 16.dp),
                text = "This Week",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Green500
            )
            TrainingCalendar(modifier = modifier, startDate = 2, endDate = 8)
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
                color = Green500,
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
fun TrainingCalendar(
    modifier: Modifier,
    startDate: Int,
    endDate: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (i in startDate..endDate) {
            CalendarDayItem(modifier = modifier, date = i + 1)
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
            color = Green500
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
                    color = Green500
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
                append(pluralOrNot(numberDouble ?: numberInt!!.toDouble(), suffix))

            }
        }
    )
}

fun pluralOrNot(
    number: Double,
    suffix: String
): String = if (number != 1.0) "${suffix}s"
else suffix