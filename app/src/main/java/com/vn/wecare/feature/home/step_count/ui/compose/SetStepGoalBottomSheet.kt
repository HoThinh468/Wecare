package com.vn.wecare.feature.home.step_count.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.training.widget.numberPickerSpinner
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.WecareUserConstantValues.MAX_STEPS_GOAL
import com.vn.wecare.utils.WecareUserConstantValues.MIN_STEPS_GOAL

@Composable
fun SetYourStepCountGoalModalBottomSheetContent(
    modifier: Modifier, onCloseClick: () -> Unit, stepCountViewModel: StepCountViewModel
) {

    val currentIndex = rememberSaveable { mutableStateOf(MIN_STEPS_GOAL) }

    Column(
        modifier = modifier
            .heightIn()
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = midPadding, vertical = mediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Steps", style = MaterialTheme.typography.h3)
        currentIndex.value = numberPickerSpinner(
            modifier = modifier,
            min = MIN_STEPS_GOAL,
            max = MAX_STEPS_GOAL,
        )
        Row(
            modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                modifier = modifier
                    .weight(1f)
                    .padding(end = smallPadding)
                    .height(40.dp),
                onClick = { onCloseClick() },
                shape = RoundedCornerShape(mediumRadius)
            ) {
                Text(text = stringResource(id = R.string.close_dialog_title))
            }
            Button(
                modifier = modifier
                    .weight(1f)
                    .padding(start = smallPadding)
                    .height(40.dp),
                onClick = {
                    onCloseClick()
                    stepCountViewModel.updateGoal(if (currentIndex.value < 1000) 1000 else currentIndex.value)
                },
                shape = RoundedCornerShape(mediumRadius)
            ) {
                Text(text = stringResource(id = R.string.okay_dialog_title))
            }
        }
    }
}