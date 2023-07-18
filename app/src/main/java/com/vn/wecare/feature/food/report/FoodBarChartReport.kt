package com.vn.wecare.feature.food.report

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.utils.common_composable.BarChartItem
import com.vn.wecare.utils.common_composable.LoadingDialog

@Composable
fun FoodBarChartReport(
    modifier: Modifier, viewModel: FoodReportViewModel, onNavigateUp: () -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()

    uiState.value.isLoadingData.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Error -> {
                onNavigateUp()
                Toast.makeText(
                    LocalContext.current, "Loading data fail!", Toast.LENGTH_SHORT
                ).show()
            }

            is Response.Success -> {
                Toast.makeText(
                    LocalContext.current, "Loading data successfully!", Toast.LENGTH_SHORT
                ).show()
            }

            else -> {/* Do nothing */
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
        elevation = smallElevation,
        shape = Shapes.small,
    ) {
        Column(
            modifier = modifier.padding(midPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "${uiState.value.firstDayOfWeek} - ${uiState.value.lastDayOfWeek}",
                    style = MaterialTheme.typography.h4
                )
                Row {
                    IconButton(onClick = {
                        viewModel.onPreviousWeekClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                    IconButton(enabled = uiState.value.isNextClickEnable, onClick = {
                        viewModel.onNextWeekClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = if (uiState.value.isNextClickEnable) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(midPadding))
            if (uiState.value.isAbleToShowBarChart) {
                Row(
                    modifier = modifier
                        .weight(10f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (item in uiState.value.dayReportList) {
                        BarChartItem(
                            itemTitle = item.dayOfWeek,
                            progress = item.progress,
                            index = item.caloriesInTake
                        )
                    }
                }
            } else {
                Image(
                    painter = painterResource(id = R.drawable.img_no_meal_available),
                    modifier = modifier.size(200.dp),
                    contentDescription = null
                )
                Text(
                    text = "No report for this week!",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = modifier.height(halfMidPadding))
            Row(
                modifier = modifier.weight(2f), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier
                        .size(40.dp)
                        .weight(1f),
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = Red400
                )
                Column(
                    modifier = modifier
                        .weight(6f)
                        .padding(horizontal = halfMidPadding)
                ) {
                    Text(text = "Average", style = MaterialTheme.typography.body2)
                    Text(
                        text = "${uiState.value.averageAmount} cal",
                        style = MaterialTheme.typography.h2
                    )
                }
                Image(
                    modifier = modifier
                        .weight(1f)
                        .size(56.dp),
                    painter = painterResource(id = R.drawable.img_girl_with_food),
                    contentDescription = null
                )
            }
        }
    }
}