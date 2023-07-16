package com.vn.wecare.feature.account.view.editinfo.dialog

import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.vn.wecare.feature.home.goal.data.model.ActivityLevel
import com.vn.wecare.feature.onboarding.composable.OnboardingActivityLevelPicker
import com.vn.wecare.ui.theme.Shapes

@Composable
fun PickActivityLevelDialog(
    modifier: Modifier,
    dismissDialog: () -> Unit,
    onActivityLevelClick: (level: ActivityLevel) -> Unit,
    currentChosenLevel: ActivityLevel
) {
    Dialog(onDismissRequest = { dismissDialog() }) {
        Card(shape = Shapes.medium) {
            OnboardingActivityLevelPicker(
                modifier = modifier, onActivityLevelClick = {
                    onActivityLevelClick(it)
                    dismissDialog()
                }, chosenLevel = currentChosenLevel
            )
        }
    }
}