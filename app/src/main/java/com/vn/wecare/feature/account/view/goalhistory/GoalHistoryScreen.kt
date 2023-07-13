package com.vn.wecare.feature.account.view.goalhistory

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.viewmodel.GoalHistoryViewModel
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.YellowStar
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.WecareAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalHistoryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: GoalHistoryViewModel,
    moveToEditInfoScreen: (goal: Goal) -> Unit
) {

    val goals = viewModel.goals.collectAsState().value
    val filterUi = viewModel.filterUi.collectAsState().value
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val uiState = viewModel.detailUiState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    filterUi.getDataResponse.let {
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

    ModalBottomSheetLayout(
        sheetContent = {
            GoalDetailModalBottomSheet(goal = viewModel.currentChosenGoal.collectAsState().value,
                resetGoal = {
                    moveToEditInfoScreen(it)
                },
                closeBottomSheet = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                uiState = uiState,
                resetGetRecordResponse = viewModel::resetGetRecordResponse
            )
        },
        sheetState = sheetState,
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                WecareAppBar(
                    modifier = modifier,
                    onLeadingIconPress = navigateBack,
                    title = "Goal history",
                )
            },
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(normalPadding)
            ) {
                items(goals.size) {
                    GoalHistoryItem(modifier = modifier, goal = goals[it], onItemClick = { goal ->
                        viewModel.onGoalSelected(goal)
                        viewModel.getRecords(goal)
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalHistoryItem(modifier: Modifier, goal: Goal, onItemClick: (goal: Goal) -> Unit) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = normalPadding),
        shape = Shapes.medium,
        elevation = smallElevation,
        onClick = { onItemClick(goal) }) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier.height(IntrinsicSize.Max),
            ) {
                Text(
                    text = goal.goalName, style = MaterialTheme.typography.h2
                )
                Spacer(modifier = modifier.height(smallPadding))
                Box(
                    modifier = modifier
                        .clip(Shapes.medium)
                        .background(getColorBasedOnStatus(status = goal.goalStatus))
                ) {
                    Text(
                        modifier = modifier.padding(vertical = 6.dp, horizontal = smallPadding),
                        text = goal.goalStatus,
                        style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.onPrimary)
                    )
                }
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
                            getDayFromLongWithFormat(goal.dateSetGoal),
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
                            getDayFromLongWithFormat(goal.dateEndGoal),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
            Image(
                modifier = modifier.size(120.dp), painter = painterResource(
                    id = getImgResBasedOnGoal(goal.goalName)
                ), contentDescription = null
            )
        }
    }
}

private fun getImgResBasedOnGoal(name: String): Int {
    return when (name) {
        EnumGoal.GETHEALTHIER.value -> R.drawable.img_illu_healthier
        EnumGoal.LOSEWEIGHT.value -> R.drawable.img_illu_loose_weight
        EnumGoal.GAINMUSCLE.value -> R.drawable.img_illu_muscle
        else -> R.drawable.img_illu_improve_mood
    }
}

@Composable
fun getColorBasedOnStatus(status: String): Color {
    return when (status) {
        GoalStatus.INPROGRESS.value -> Blue
        GoalStatus.SUCCESS.value -> YellowStar
        GoalStatus.CANCELED.value -> Red400
        else -> MaterialTheme.colors.primary
    }
}