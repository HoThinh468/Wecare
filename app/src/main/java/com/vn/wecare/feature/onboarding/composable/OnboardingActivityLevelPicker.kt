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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.vn.wecare.feature.home.goal.data.model.ActivityLevel
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun OnboardingActivityLevelPicker(
    modifier: Modifier,
    onActivityLevelClick: (level: ActivityLevel) -> Unit,
    chosenLevel: ActivityLevel
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
            text = "How active you are?",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = modifier.padding(horizontal = mediumPadding),
            text = "We need to know your activity level",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(mediumPadding))
        OnboardingItemPicker(
            modifier = modifier,
            onClick = {
                onActivityLevelClick(ActivityLevel.SEDENTARY)
            },
            title = ActivityLevel.SEDENTARY.value,
            subtitle = ActivityLevel.SEDENTARY.description,
            borderColor = getBorderColor(
                chosenItem = chosenLevel, currentItem = ActivityLevel.SEDENTARY
            )
        )
        Spacer(modifier = modifier.height(normalPadding))
        OnboardingItemPicker(
            modifier = modifier,
            onClick = {
                onActivityLevelClick(ActivityLevel.LIGHTLYACTIVE)
            },
            title = ActivityLevel.LIGHTLYACTIVE.value,
            subtitle = ActivityLevel.LIGHTLYACTIVE.description,
            borderColor = getBorderColor(
                chosenItem = chosenLevel, currentItem = ActivityLevel.LIGHTLYACTIVE
            )
        )
        Spacer(modifier = modifier.height(normalPadding))
        OnboardingItemPicker(
            modifier = modifier,
            onClick = { onActivityLevelClick(ActivityLevel.MODERATELYACTIVE) },
            title = ActivityLevel.MODERATELYACTIVE.value,
            subtitle = ActivityLevel.MODERATELYACTIVE.description,
            borderColor = getBorderColor(
                chosenItem = chosenLevel, currentItem = ActivityLevel.MODERATELYACTIVE
            )
        )
        Spacer(modifier = modifier.height(normalPadding))
        OnboardingItemPicker(
            modifier = modifier,
            onClick = { onActivityLevelClick(ActivityLevel.ACTIVE) },
            title = ActivityLevel.ACTIVE.value,
            subtitle = ActivityLevel.ACTIVE.description,
            borderColor = getBorderColor(
                chosenItem = chosenLevel, currentItem = ActivityLevel.ACTIVE
            )
        )
        Spacer(modifier = modifier.height(halfMidPadding))
    }
}

@Composable
fun getBorderColor(chosenItem: Any, currentItem: Any): Color {
    return if (chosenItem == currentItem) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
}


