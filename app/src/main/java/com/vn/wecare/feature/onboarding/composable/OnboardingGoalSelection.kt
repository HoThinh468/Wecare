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
import androidx.compose.ui.text.style.TextAlign
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun OnboardingGoalSelection(
    modifier: Modifier,
    onGoalClick: (goal: EnumGoal) -> Unit,
    chosenGoal: EnumGoal,
    recommendedGoal: EnumGoal
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = modifier.padding(vertical = smallPadding),
            text = "Choose your goal",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = modifier.padding(horizontal = mediumPadding),
            text = "Have a clear goal will help you achieve it easier",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(mediumPadding))
        OnboardingItemPicker(
            modifier = modifier,
            onClick = { onGoalClick(EnumGoal.GAINWEIGHT) },
            title = EnumGoal.GAINWEIGHT.value,
            subtitle = if (recommendedGoal == EnumGoal.GAINWEIGHT) "(Recommended)" else null,
            borderColor = getBorderColor(chosenItem = chosenGoal, currentItem = EnumGoal.GAINWEIGHT)
        )
        Spacer(modifier = modifier.height(normalPadding))
        OnboardingItemPicker(
            modifier = modifier,
            onClick = { onGoalClick(EnumGoal.MAINTAINWEIGHT) },
            title = EnumGoal.MAINTAINWEIGHT.value,
            subtitle = if (recommendedGoal == EnumGoal.MAINTAINWEIGHT) "(Recommended)" else null,
            borderColor = getBorderColor(
                chosenItem = chosenGoal, currentItem = EnumGoal.MAINTAINWEIGHT
            )
        )
        Spacer(modifier = modifier.height(normalPadding))
        OnboardingItemPicker(
            modifier = modifier,
            onClick = { onGoalClick(EnumGoal.LOSEWEIGHT) },
            title = EnumGoal.LOSEWEIGHT.value,
            subtitle = if (recommendedGoal == EnumGoal.LOSEWEIGHT) "(Recommended)" else null,
            borderColor = getBorderColor(chosenItem = chosenGoal, currentItem = EnumGoal.LOSEWEIGHT)
        )
        Spacer(modifier = modifier.height(halfMidPadding))
    }
}