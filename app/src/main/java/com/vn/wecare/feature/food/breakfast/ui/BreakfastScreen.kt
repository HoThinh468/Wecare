package com.vn.wecare.feature.food.breakfast.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.breakfast.viewmodel.BreakfastViewModel
import com.vn.wecare.feature.food.data.model.MealRecordModel
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
import com.vn.wecare.ui.theme.xxExtraPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.common_composable.WecareAppBar
import com.vn.wecare.utils.getProgressInFloatWithIntInput
import java.util.Calendar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BreakfastScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    moveToAddMealScreen: () -> Unit,
    breakfastViewModel: BreakfastViewModel
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
                .padding(horizontal = midPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(smallPadding))
            BreakfastOverviewNutrition(modifier = modifier, viewModel = breakfastViewModel)
            Spacer(modifier = modifier.height(midPadding))
            MealRecord(modifier = modifier, viewModel = breakfastViewModel)
        }
    }
}

@Composable
private fun BreakfastOverviewNutrition(
    modifier: Modifier, viewModel: BreakfastViewModel
) {

    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.onDateChangeListener(mDayOfMonth, mMonth, mYear)
        }, year, month, day
    )

    val uiState = viewModel.uiState.collectAsState()

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
                Text(text = uiState.value.dateTime, style = MaterialTheme.typography.h4)
                IconButton(modifier = modifier.size(32.dp), onClick = { datePickerDialog.show() }) {
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
                        append(uiState.value.calories.toString())
                    }
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.Black450),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            fontFamily = OpenSans
                        )
                    ) {
                        append("/${uiState.value.targetCalories} kcal")
                    }
                })
            LinearProgressIndicator(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = halfMidPadding, bottom = halfMidPadding)
                    .height(6.dp),
                strokeCap = StrokeCap.Round,
                progress = getProgressInFloatWithIntInput(
                    uiState.value.calories, uiState.value.targetCalories
                ),
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
                    currentIndex = uiState.value.protein,
                    targetIndex = uiState.value.targetProtein
                )
                NutritionIndexItem(
                    modifier = modifier,
                    color = Yellow,
                    title = "Fat",
                    currentIndex = uiState.value.fat,
                    targetIndex = uiState.value.targetFat
                )
                NutritionIndexItem(
                    modifier = modifier,
                    color = Blue,
                    title = "Carbs",
                    currentIndex = uiState.value.carbs,
                    targetIndex = uiState.value.targetCarbs
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
private fun MealRecord(modifier: Modifier, viewModel: BreakfastViewModel) {

    val uiState = viewModel.uiState.collectAsState()

    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        text = "Meal records",
        style = MaterialTheme.typography.h4
    )

    val isNetworkAvailable = checkInternetConnection(LocalContext.current)

    if (isNetworkAvailable) {
        uiState.value.getMealsResponse.let {
            when (it) {
                is Response.Loading -> {
                    CircularProgressIndicator(
                        modifier = modifier.padding(vertical = xxxExtraPadding),
                        color = MaterialTheme.colors.primary
                    )
                }

                is Response.Success -> {
                    if (uiState.value.mealRecords.isNotEmpty()) {
                        LazyColumn {
                            items(uiState.value.mealRecords.size) { i ->
                                val item = uiState.value.mealRecords[i]
                                MealRecordItem(
                                    modifier = modifier, mealRecordModel = item
                                )
                            }
                        }
                    } else {
                        Image(
                            modifier = modifier.padding(top = xxExtraPadding, bottom = midPadding),
                            painter = painterResource(id = R.drawable.img_empty_dish),
                            contentDescription = null
                        )
                        Text(
                            text = "No records yet, click +Add meal to add now!",
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is Response.Error -> {
                    Image(
                        modifier = modifier.padding(top = xxExtraPadding, bottom = midPadding),
                        painter = painterResource(id = R.drawable.img_oops),
                        contentDescription = null
                    )
                    Text(
                        text = "Something wrong happened, please try again later!!",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }

                else -> { // Do nothing
                }
            }
        }
    } else {
        Image(
            modifier = modifier.padding(top = xxExtraPadding, bottom = midPadding),
            painter = painterResource(id = R.drawable.img_no_internet),
            contentDescription = null
        )
        Text(
            text = "No internet connection, please connect to the internet to view records",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MealRecordItem(
    modifier: Modifier, mealRecordModel: MealRecordModel
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
                AsyncImage(
                    modifier = modifier
                        .size(48.dp)
                        .clip(shape = Shapes.medium),
                    model = mealRecordModel.imgUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(modifier = modifier.padding(start = normalPadding)) {
                    Text(
                        modifier = modifier.width(200.dp),
                        text = mealRecordModel.title,
                        style = MaterialTheme.typography.h5,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = modifier.height(4.dp))
                    Text(
                        text = "${mealRecordModel.calories} cal",
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
                Text(text = "${mealRecordModel.quantity}", style = MaterialTheme.typography.body1)
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