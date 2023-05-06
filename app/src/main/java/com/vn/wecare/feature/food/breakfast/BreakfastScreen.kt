package com.vn.wecare.feature.food.breakfast

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.OpenSans
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BreakfastScreen(
    modifier: Modifier = Modifier, navigateUp: () -> Unit, moveToAddMealScreen: () -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            WecareAppBar(
                modifier = modifier, title = "Breakfast", onLeadingIconPress = navigateUp
            )
        },
        bottomBar = {
            Button(
                modifier = modifier
                    .padding(midPadding)
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = { moveToAddMealScreen() },
                shape = Shapes.large
            ) {
                Icon(
                    modifier = modifier
                        .padding(end = smallPadding)
                        .size(16.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
                Text(text = "Add meal", style = MaterialTheme.typography.button)
            }
        }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = midPadding)
        ) {
            Spacer(modifier = modifier.height(smallPadding))
            BreakfastOverviewNutrition(modifier = modifier)
            Spacer(modifier = modifier.height(midPadding))
            MealRecord(modifier = modifier)
        }
    }
}

@Composable
private fun BreakfastOverviewNutrition(modifier: Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        shape = Shapes.medium,
        elevation = smallElevation
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = normalPadding, horizontal = midPadding),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Today, 03 Mar", style = MaterialTheme.typography.h4)
                IconButton(modifier = modifier.size(32.dp), onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.EditCalendar,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Text(modifier = modifier
                .fillMaxWidth()
                .padding(top = smallPadding),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            fontFamily = OpenSans
                        )
                    ) {
                        append("170")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.Black450),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            fontFamily = OpenSans
                        )
                    ) {
                        append("/400 kcal")
                    }
                })
            LinearProgressIndicator(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = halfMidPadding, bottom = halfMidPadding)
                    .height(6.dp),
                strokeCap = StrokeCap.Round,
                progress = 0.5f,
                backgroundColor = MaterialTheme.colors.secondary.copy(0.4f)
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NutritionIndexItem(
                    modifier = modifier,
                    color = Red400,
                    title = "Protein",
                    currentIndex = 20,
                    targetIndex = 25
                )
                NutritionIndexItem(
                    modifier = modifier,
                    color = Yellow,
                    title = "Fat",
                    currentIndex = 10,
                    targetIndex = 14
                )
                NutritionIndexItem(
                    modifier = modifier,
                    color = Blue,
                    title = "Carbs",
                    currentIndex = 32,
                    targetIndex = 46
                )
            }
        }
    }
}

@Composable
fun NutritionIndexItem(
    modifier: Modifier,
    color: Color,
    title: String,
    currentIndex: Int,
    targetIndex: Int,
    unit: String = "g"
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = modifier
                .size(12.dp)
                .background(color = color, shape = CircleShape)
        )
        Column(modifier = modifier.padding(start = smallPadding)) {
            Text(text = title, style = MaterialTheme.typography.body2)
            Text(
                text = "${currentIndex}/$targetIndex $unit",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
private fun MealRecord(modifier: Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        text = "Meal records",
        style = MaterialTheme.typography.h4
    )
    MealRecordItem(
        modifier = modifier, foodName = "Sandwich", calAmount = 176, foodAmount = 100, quantity = 1
    )
    MealRecordItem(
        modifier = modifier,
        foodName = "Eggs spaghetti",
        calAmount = 26,
        foodAmount = 200,
        quantity = 3,
    )
    MealRecordItem(
        modifier = modifier,
        foodName = "Buffalo wings",
        calAmount = 76,
        foodAmount = 160,
        quantity = 4
    )
}

@Composable
private fun MealRecordItem(
    modifier: Modifier,
    foodName: String,
    calAmount: Int,
    foodAmount: Int,
    quantity: Int,
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = normalPadding)
        ) {
            Row(
                modifier = modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_food_example),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(48.dp)
                        .clip(shape = Shapes.medium)
                )
                Column(modifier = modifier.padding(start = normalPadding)) {
                    Text(
                        modifier = modifier.width(200.dp),
                        text = foodName,
                        style = MaterialTheme.typography.h5,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = modifier.height(4.dp))
                    Text(
                        text = "$foodAmount g ~ $calAmount kcal",
                        style = MaterialTheme.typography.button.copy(
                            color = MaterialTheme.colors.onSecondary.copy(
                                0.5f
                            )
                        )
                    )
                }
            }
            Row(
                modifier = modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_remove),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(text = "$quantity", style = MaterialTheme.typography.body1)
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
        Divider()
    }
}