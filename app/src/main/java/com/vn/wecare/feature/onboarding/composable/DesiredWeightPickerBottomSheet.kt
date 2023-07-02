package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.goal.EnumGoal
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxExtraPadding
import com.vn.wecare.utils.WecareUserConstantValues.MAX_DIFFERENCE_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_DIFFERENCE_WEIGHT

@Composable
fun DesiredWeightPickerBottomSheet(
    modifier: Modifier = Modifier,
    closeBottomSheet: () -> Unit,
    onDesiredWeightDifferencePickScrolled: (weight: Int) -> Unit,
    desiredWeightDifference: Int,
    goal: EnumGoal,
    estimatedTime: Int,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseOnboardingContent(
            modifier = modifier,
            titleRes = if (goal == EnumGoal.GAINMUSCLE) R.string.Gain_weight_title else R.string.Lose_weight_title,
            subtitleRes = R.string.desired_weight_picker_subtitle
        ) {
            OnboardingWeightPicker(modifier = modifier,
                weightPicked = desiredWeightDifference,
                onPickWeightScrolled = {
                    onDesiredWeightDifferencePickScrolled(it)
                },
                customContent = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(text = "$desiredWeightDifference", style = MaterialTheme.typography.h1)
                            Text(text = "kg", style = MaterialTheme.typography.body2)
                        }
                        Spacer(modifier = modifier.height(smallPadding))
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "Time estimated: $estimatedTime ",
                                style = MaterialTheme.typography.h5
                            )
                            Text(
                                text = if (estimatedTime == 1) "week" else "weeks",
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                },
                minWeight = MIN_DIFFERENCE_WEIGHT,
                maxWeight = MAX_DIFFERENCE_WEIGHT
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(midPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                modifier = modifier
                    .weight(1f)
                    .padding(end = smallPadding)
                    .height(40.dp),
                onClick = {
                    closeBottomSheet()
                },
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
                    closeBottomSheet()
                },
                shape = RoundedCornerShape(mediumRadius)
            ) {
                Text(text = "Continue")
            }
        }
        Spacer(modifier = modifier.height(xxExtraPadding))
    }
}