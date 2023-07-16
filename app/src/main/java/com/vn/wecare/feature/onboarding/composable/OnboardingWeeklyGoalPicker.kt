package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.home.goal.data.model.ActivityLevel
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun OnboardingWeeklyGoalPicker(
    modifier: Modifier,
    desiredWeightDifference: Int,
    chosenGoal: EnumGoal,
    activityLevel: ActivityLevel,
    onWeeklyGoalSelected: (weight: Float) -> Unit,
    chosenWeeklyGoal: Float,
    recommendedWeeklyGoal: Float,
    openPickWeightBottomSheet: () -> Unit,
    warningMessage: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = modifier.height(mediumPadding))
        Text(
            text = if (chosenGoal == EnumGoal.GAINWEIGHT) "How many weight do you want to gain?" else "How many weight do you want to gain?",
            style = MaterialTheme.typography.body1,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(normalPadding))
        Box(modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = if (warningMessage == null) MaterialTheme.colors.primary else Red400
                )
            )
            .clickable {
                openPickWeightBottomSheet()
            }) {
            Text(
                text = "$desiredWeightDifference kg",
                style = MaterialTheme.typography.h5,
                modifier = modifier
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
        Spacer(modifier = modifier.height(smallPadding))
        if (warningMessage != null) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = warningMessage,
                style = MaterialTheme.typography.caption.copy(Red400)
            )
        }
        Spacer(modifier = modifier.height(mediumPadding))
        Text(
            text = "What is your weekly goal?",
            style = MaterialTheme.typography.body1,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(normalPadding))
        OnboardingItemPicker(
            modifier = modifier,
            onClick = { onWeeklyGoalSelected(0.25f) },
            title = if (chosenGoal == EnumGoal.GAINWEIGHT) "Gain 0.25 kg per week" else "Lose 0.25 kg per week",
            subtitle = if (recommendedWeeklyGoal == 0.25f) "(Recommended)" else null,
            borderColor = if (chosenWeeklyGoal == 0.25f) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        )
        Spacer(modifier = modifier.height(normalPadding))
        OnboardingItemPicker(
            modifier = modifier,
            onClick = { onWeeklyGoalSelected(0.5f) },
            title = if (chosenGoal == EnumGoal.GAINWEIGHT) "Gain 0.5 kg per week" else "Lose 0.5 kg per week",
            subtitle = if (recommendedWeeklyGoal == 0.5f) "(Recommended)" else null,
            borderColor = if (chosenWeeklyGoal == 0.5f) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        )
        if (activityLevel == ActivityLevel.MODERATELYACTIVE || activityLevel == ActivityLevel.ACTIVE) {
            Spacer(modifier = modifier.height(normalPadding))
            OnboardingItemPicker(
                modifier = modifier,
                onClick = { onWeeklyGoalSelected(1f) },
                title = if (chosenGoal == EnumGoal.GAINWEIGHT) "Gain 1 kg per week" else "Lose 1 kg per week",
                subtitle = if (recommendedWeeklyGoal == 1f) "(Recommended)" else null,
                borderColor = if (chosenWeeklyGoal == 1f) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
            )
        }
    }
}