package com.vn.wecare.feature.account.view.editinfo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.account.view.editinfo.dialog.PickActivityLevelDialog
import com.vn.wecare.feature.account.view.editinfo.dialog.PickGoalDialog
import com.vn.wecare.feature.account.view.editinfo.dialog.PickWeeklyGoalDialog
import com.vn.wecare.feature.account.viewmodel.EditInfoViewModel
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.onboarding.composable.OnboardingItemPicker
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun EditInfoGoalSelectionSection(
    modifier: Modifier, viewModel: EditInfoViewModel, openBottomSheet: () -> Unit
) {

    val uiState = viewModel.editGoalInfoUiState.collectAsState().value

    val openPickActivityLevelDialog = remember {
        mutableStateOf(false)
    }

    val openPickGoalDialog = remember {
        mutableStateOf(false)
    }

    val openPickWeeklyGoalDialog = remember { mutableStateOf(false) }

    if (openPickActivityLevelDialog.value) {
        PickActivityLevelDialog(
            modifier = modifier,
            dismissDialog = {
                openPickActivityLevelDialog.value = false
            },
            currentChosenLevel = uiState.currentChosenActivityLevel,
            onActivityLevelClick = {
                viewModel.onActivityLevelSelected(it)
            },
        )
    }

    if (openPickGoalDialog.value) {
        PickGoalDialog(
            modifier = modifier,
            dismissDialog = {
                openPickGoalDialog.value = false
            },
            currentChosenGoal = uiState.currentChosenGoal,
            onGoalClick = {
                viewModel.onGoalSelected(it)
            },
        )
    }

    if (openPickWeeklyGoalDialog.value) {
        PickWeeklyGoalDialog(modifier = modifier,
            dismissDialog = { openPickWeeklyGoalDialog.value = false },
            onWeeklyGoalSelected = {
                viewModel.onWeeklyGoalWeightSelected(it)
                openPickWeeklyGoalDialog.value = false
            },
            chosenGoal = uiState.currentChosenGoal,
            chosenWeeklyGoal = uiState.weeklyGoalWeight,
            chosenActivityLevel = uiState.currentChosenActivityLevel
        )
    }

    if (!uiState.isGoalExpired) {
        Text(
            text = "* Note: You have to complete your current goal to edit those fields",
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = modifier.height(smallPadding))
    }
    Text(
        modifier = modifier.padding(bottom = halfMidPadding),
        text = "Activity level",
        style = MaterialTheme.typography.body1
    )
    OnboardingItemPicker(
        modifier = modifier,
        onClick = { openPickActivityLevelDialog.value = true },
        title = uiState.currentChosenActivityLevel.value,
        subtitle = uiState.currentChosenActivityLevel.description,
        borderColor = if (uiState.isGoalExpired) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
        enabled = uiState.isGoalExpired
    )
    Spacer(modifier = modifier.height(normalPadding))
    Text(
        modifier = modifier.padding(bottom = halfMidPadding),
        text = "Your goal",
        style = MaterialTheme.typography.body1
    )
    OnboardingItemPicker(
        modifier = modifier,
        onClick = {
            openPickGoalDialog.value = true
        },
        title = uiState.currentChosenGoal.value,
        subtitle = null,
        borderColor = if (uiState.isGoalExpired) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
        enabled = uiState.isGoalExpired
    )
    Spacer(modifier = modifier.height(normalPadding))
    if (uiState.currentChosenGoal != EnumGoal.MAINTAINWEIGHT) {
        Spacer(modifier = modifier.height(normalPadding))
        Text(
            modifier = modifier.padding(bottom = halfMidPadding),
            text = "Weight to lose",
            style = MaterialTheme.typography.body1
        )
        Box(modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = if (uiState.isGoalExpired) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                )
            )
            .clickable {
                if (uiState.isGoalExpired) {
                    openBottomSheet()
                }
            }) {
            Text(
                text = "1 kg", style = MaterialTheme.typography.h5, modifier = modifier
                    .align(
                        Alignment.CenterStart
                    )
                    .padding(normalPadding)
            )
            Icon(
                modifier = modifier
                    .align(Alignment.CenterEnd)
                    .padding(normalPadding),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
        Spacer(modifier = modifier.height(normalPadding))
        if (uiState.currentChosenGoal != EnumGoal.MAINTAINWEIGHT) {
            Text(
                modifier = modifier.padding(bottom = halfMidPadding),
                text = "Weekly goal",
                style = MaterialTheme.typography.body1
            )
            OnboardingItemPicker(
                modifier = modifier,
                onClick = {
                    openPickWeeklyGoalDialog.value = true
                },
                title = if (uiState.currentChosenGoal == EnumGoal.GAINWEIGHT) "Gain ${uiState.weeklyGoalWeight} kg per week" else "Lose ${uiState.weeklyGoalWeight} kg per week",
                borderColor = if (uiState.isGoalExpired) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
                enabled = uiState.isGoalExpired
            )
        }
    }
}
