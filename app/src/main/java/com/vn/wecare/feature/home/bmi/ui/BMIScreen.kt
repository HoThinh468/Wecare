package com.vn.wecare.feature.home.bmi.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.bmi.data.BMIFAQsModel
import com.vn.wecare.feature.home.bmi.viewmodel.BMIUiState
import com.vn.wecare.feature.home.bmi.viewmodel.BMIViewModel
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.midRadius
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.bmiFormatWithFloat
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.VerticalExpandableView
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BMIScreen(
    modifier: Modifier = Modifier, navigateUp: () -> Unit, viewModel: BMIViewModel
) {

    val uiState = viewModel.uiState.collectAsState()

    uiState.value.updateInformationResult.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(
                    LocalContext.current, "Update information success!", Toast.LENGTH_SHORT
                ).show()
                viewModel.resetUpdateInformation()
            }

            is Response.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    "Update information fail. Please try later!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetUpdateInformation()
            }

            else -> { /* do nothing */
            }
        }
    }

    Scaffold(modifier = modifier, backgroundColor = MaterialTheme.colors.background, topBar = {
        WecareAppBar(modifier = modifier,
            onLeadingIconPress = navigateUp,
            title = "BMI",
            trailingIconRes = R.drawable.ic_timeline,
            onTrailingIconPress = { // TODO navigate to history screen
            })
    }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(midPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserInformation(modifier = modifier, uiState = uiState.value, viewModel = viewModel)
            Spacer(modifier = modifier.height(midPadding))
            BMIOverview(
                modifier = modifier, bmi = uiState.value.bmi, progress = uiState.value.bmiProgress
            )
            Spacer(modifier = modifier.height(smallPadding))
            BMIFAQsSection(modifier = modifier, faqList = viewModel.getFAQs())
        }
    }
}

@Composable
private fun UserInformation(modifier: Modifier, uiState: BMIUiState, viewModel: BMIViewModel) {

    var openUpdateWeightDialog by remember { mutableStateOf(false) }
    var openUpdateHeightDialog by remember { mutableStateOf(false) }

    Row(modifier = modifier.fillMaxWidth()) {
        Image(
            modifier = modifier
                .height(270.dp)
                .width(150.dp)
                .weight(1f), painter = painterResource(
                id = if (uiState.gender) R.drawable.img_boy_standing
                else R.drawable.img_girl_standing
            ), contentDescription = null
        )

        Column(
            modifier = modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    uiState.weight.toString(),
                    style = MaterialTheme.typography.h1.copy(fontSize = 40.sp)
                )
                IconButton(
                    modifier = modifier.size(18.dp),
                    onClick = { openUpdateWeightDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = null
                    )
                }
            }
            Text("Kilograms", style = MaterialTheme.typography.body2)
            Spacer(modifier = modifier.height(normalPadding))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    uiState.height.toString(),
                    style = MaterialTheme.typography.h1.copy(fontSize = 40.sp)
                )
                IconButton(
                    modifier = modifier.size(18.dp),
                    onClick = { openUpdateHeightDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = null
                    )
                }
            }
            Text("Centimeters", style = MaterialTheme.typography.body2)
            Spacer(modifier = modifier.height(normalPadding))
            Divider()
            Spacer(modifier = modifier.height(smallPadding))
            Text(
                bmiFormatWithFloat(uiState.bmi), style = MaterialTheme.typography.h1.copy(
                    color = if (uiState.bmi in 18.5..24.9) Green500 else getBadMoodColor(uiState.bmi),
                    fontSize = 40.sp
                )
            )
            Text(
                "BMI", style = MaterialTheme.typography.body2.copy(
                    color = if (uiState.bmi in 18.5..24.9) getHappyMoodColor(
                        uiState.bmi
                    ) else getBadMoodColor(uiState.bmi)
                )
            )
        }

        if (openUpdateWeightDialog) {
            UpdateWeightDialog(
                modifier = modifier,
                onCloseClick = { openUpdateWeightDialog = false },
                viewModel = viewModel
            )
        }

        if (openUpdateHeightDialog) {
            UpdateHeightDialog(
                modifier = modifier,
                onCloseClick = { openUpdateHeightDialog = false },
                viewModel = viewModel
            )
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

@Composable
private fun BMIOverview(modifier: Modifier, bmi: Float, progress: Float) {
    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        shape = RoundedCornerShape(midRadius)
    ) {
        Column(
            modifier = modifier.padding(vertical = midPadding, horizontal = normalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Detail", style = MaterialTheme.typography.h5
                )
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_happy),
                        contentDescription = null,
                        tint = getHappyMoodColor(bmi)
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sad),
                        contentDescription = null,
                        tint = getBadMoodColor(bmi)
                    )
                }
            }
            BoxWithConstraints(modifier = modifier.padding(vertical = normalPadding)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(midRadius))
                    .background(
                        brush = Brush.horizontalGradient(colorStops = arrayOf(
                            0.0f to Blue, 0.37f to Green500, 0.5f to Yellow, 1f to Red400
                        ),
                            startX = with(LocalDensity.current) { 100.dp.toPx() },
                            endX = with(LocalDensity.current) { 250.dp.toPx() }
                        )
                    )
                    .align(Alignment.Center))
                Divider(
                    modifier = modifier
                        .padding(start = maxWidth.times(progress))
                        .height(24.dp)
                        .width(4.dp)
                        .align(Alignment.CenterStart),
                    color = MaterialTheme.colors.secondary
                )
            }
            Row {
                Column(modifier = modifier.weight(1f)) {
                    BMIItemIndex(modifier = modifier, color = Blue, text = "Underweight (<18.5)")
                    Spacer(modifier = modifier.height(smallPadding))
                    BMIItemIndex(modifier = modifier, color = Green500, text = "Normal (18.5-24.9)")
                }
                Column(modifier = modifier.weight(1f)) {
                    BMIItemIndex(modifier = modifier, color = Yellow, text = "Overweight (25-29.9)")
                    Spacer(modifier = modifier.height(smallPadding))
                    BMIItemIndex(modifier = modifier, color = Red400, text = "Fat (>30)")
                }
            }
        }
    }
}

@Composable
private fun BMIItemIndex(modifier: Modifier, color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier
                .size(12.dp)
                .background(color = color, shape = CircleShape)
        )
        Spacer(modifier = modifier.width(smallPadding))
        Text(text = text, style = MaterialTheme.typography.caption)
    }
}

@Composable
private fun BMIFAQsSection(modifier: Modifier, faqList: List<BMIFAQsModel>) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "FAQs", style = MaterialTheme.typography.h4)
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Ask more", style = MaterialTheme.typography.button)
        }
    }
    faqList.forEach { item ->
        BMIFAQsListItem(
            modifier = modifier, question = item.question, answer = item.answer
        )
    }
}

@Composable
private fun BMIFAQsListItem(modifier: Modifier, question: String, answer: String) {
    var isExpanded by remember { mutableStateOf(false) }
    VerticalExpandableView(header = {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = normalPadding)
                .clickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = question, style = MaterialTheme.typography.button
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null
            )
        }
    }, expandablePart = {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = halfMidPadding, start = normalPadding),
            text = answer,
            style = MaterialTheme.typography.caption
        )
    }, isExpanded = isExpanded
    )
    Divider()
}