package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.home.goal.data.model.ActivityLevel
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding

@Composable
fun OnboardingWeeklyGoalPicker(
    modifier: Modifier,
    chosenGoal: EnumGoal,
    activityLevel: ActivityLevel,
    onWeeklyGoalSelected: (weight: Float) -> Unit,
    chosenWeeklyGoal: Float,
    recommendedWeeklyGoal: Float,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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