package com.vn.wecare.feature.account.view.editinfo.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.onboarding.composable.OnboardingGoalSelection
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding

@Composable
fun PickGoalDialog(
    modifier: Modifier,
    dismissDialog: () -> Unit,
    onGoalClick: (goal: EnumGoal) -> Unit,
    currentChosenGoal: EnumGoal,
    recommendedGoal: EnumGoal = EnumGoal.NULL,
) {
    Dialog(onDismissRequest = { dismissDialog() }) {
        Card(modifier = modifier.padding(midPadding), shape = Shapes.medium) {
            OnboardingGoalSelection(
                modifier = modifier, onGoalClick = {
                    onGoalClick(it)
                    dismissDialog()
                }, chosenGoal = currentChosenGoal, recommendedGoal = recommendedGoal
            )
        }
    }
}