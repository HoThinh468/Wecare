package com.vn.wecare.feature.home.goal.weeklyrecords

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.LoadingDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalWeeklyRecordDetailScreen(
    modifier: Modifier = Modifier, navigateBack: () -> Unit, viewModel: WeeklyRecordViewModel
) {

    val uiState = viewModel.uiState.collectAsState().value

    uiState.getRecordsResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(LocalContext.current, "Load data successfully!", Toast.LENGTH_SHORT)
                    .show()
            }

            is Response.Error -> {
                Toast.makeText(LocalContext.current, it.e?.message, Toast.LENGTH_SHORT).show()
            }

            else -> { /* do nothing */
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            WeeklyRecordAppBar(modifier = modifier, navigateBack = navigateBack, uiState = uiState)
        },
    ) {
        if (uiState.records.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(normalPadding)
            ) {
                items(uiState.records.size) {
                    GoalWeeklyRecordItem(modifier = modifier, record = uiState.records[it])
                }
            }
        } else {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_illu_no_record),
                    contentDescription = null
                )
                Spacer(modifier = modifier.height(normalPadding))
                Text(
                    text = "No records available!",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun GoalWeeklyRecordItem(
    modifier: Modifier, record: GoalDailyRecord
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = normalPadding),
        shape = Shapes.medium,
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.primaryVariant)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Text(text = record.day, style = MaterialTheme.typography.h4)
            Spacer(modifier = modifier.height(smallPadding))
            Divider()
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = smallPadding)
                    .padding(horizontal = mediumPadding, vertical = smallPadding)
                    .height(IntrinsicSize.Min), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "${record.caloriesIn} cal", style = MaterialTheme.typography.h5)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropUp,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                        Text(
                            text = "Calories in",
                            style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450))
                        )
                    }
                }
                Divider(
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(horizontal = midPadding)
                        .width(1.dp)
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "${record.caloriesOut} cal", style = MaterialTheme.typography.h5)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Red400
                        )
                        Text(
                            text = "Calories out",
                            style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
                        )
                    }
                }
            }
        }
    }
}