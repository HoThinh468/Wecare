package com.vn.wecare.feature.home.goal.dashboard

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.dashboard.goaldetail.GoalDetailSection
import com.vn.wecare.feature.home.goal.dashboard.goalrecords.GoalRecordsSection
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.common_composable.LoadingDialog

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalDashboardScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: GoalDashboardViewModel,
    navigateWeeklyRecordScreen: (record: GoalWeeklyRecord) -> Unit
) {

    val tabRowItems = listOf("Detail", "Records")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val dashboardUi = viewModel.appbarUi.collectAsState().value
    val detailUi = viewModel.detailUi.collectAsState().value
    val recordUi = viewModel.recordUi.collectAsState().value
    val updateGoalUi = viewModel.updateGoalUi.collectAsState().value

    val isWarningDialogShow = remember { mutableStateOf(false) }

    recordUi.getRecordsResponse.let {
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

    updateGoalUi.updateResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(
                    LocalContext.current, "Update goal status successfully!", Toast.LENGTH_SHORT
                ).show()
            }

            is Response.Error -> {
                Toast.makeText(LocalContext.current, it.e?.message, Toast.LENGTH_SHORT).show()
            }

            else -> { /* do nothing */
            }
        }
    }

    if (isWarningDialogShow.value) {
        CancelGoalWarningDialog(
            modifier = modifier,
            onDismissDialog = { isWarningDialogShow.value = false },
            onContinuesClick = { viewModel.updateGoalStatusToCanceled() },
        )
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            GoalDashboardAppBar(
                modifier = modifier,
                navigateBack = navigateBack,
                tabRowItems = tabRowItems,
                pagerState = pagerState,
                coroutineScope = coroutineScope,
                dashboardUI = dashboardUi
            )
        },
        bottomBar = {
            Button(
                modifier = modifier
                    .padding(horizontal = xxxExtraPadding, vertical = midPadding)
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = {
                    isWarningDialogShow.value = true
                },
                shape = Shapes.large,
                colors = ButtonDefaults.buttonColors(backgroundColor = Red400),
                enabled = updateGoalUi.isCancelGoalEnabled
            ) {
                Icon(
                    modifier = modifier
                        .padding(end = smallPadding)
                        .size(16.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "Cancel this goal",
                    style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onPrimary)
                )
            }
        }) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = midPadding, horizontal = normalPadding),
            pageCount = tabRowItems.size,
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = false
        ) {
            if (it == 0) {
                GoalDetailSection(
                    modifier = modifier, detailUi = detailUi
                )
            } else {
                GoalRecordsSection(
                    modifier = modifier,
                    records = recordUi.records,
                    onItemClick = navigateWeeklyRecordScreen
                )
            }
        }
    }
}

@Composable
fun CancelGoalWarningDialog(
    modifier: Modifier, onDismissDialog: () -> Unit, onContinuesClick: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissDialog() }) {
        Card(
            shape = Shapes.large
        ) {
            Column(
                modifier = modifier.padding(midPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Are you sure you want to cancel this goal",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
                Image(
                    modifier = modifier.padding(vertical = midPadding),
                    painter = painterResource(id = R.drawable.img_yellow_warning),
                    contentDescription = null
                )
                Text(
                    text = "If you do so your progress will be stopped and others sources like calories in and out will not be recorded to this goal!",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(midPadding))
                Button(modifier = modifier
                    .fillMaxWidth()
                    .height(40.dp), onClick = {
                    onContinuesClick()
                    onDismissDialog()
                }) {
                    Text(
                        "STILL CONTINUE", style = MaterialTheme.typography.button
                    )
                }
                TextButton(onClick = { onDismissDialog() }) {
                    Text(text = "GET ME BACK", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}