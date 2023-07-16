package com.vn.wecare.feature.onboarding.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_MUSCLE
import com.vn.wecare.utils.WecareUserConstantValues.GET_HEALTHIER
import com.vn.wecare.utils.WecareUserConstantValues.IMPROVE_MOOD
import com.vn.wecare.utils.WecareUserConstantValues.LOSE_WEIGHT

@Composable
fun EditInfoGoalSelection(
    modifier: Modifier,
    onGoalSelect: (goal: EnumGoal) -> Unit,
    openDesiredWeightPickerBottomSheet: () -> Unit,
    goalSelectedId: EnumGoal,
    isChooseGoalEnabled: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding)
            .height(300.dp)
    ) {
        RoundedIconButton(
            modifier = modifier.align(alignment = Alignment.TopStart),
            iconRes = R.drawable.ic_muscle,
            buttonText = GAIN_MUSCLE,
            bgColor = getBgColor(goalId = EnumGoal.GAINMUSCLE, currentGoal = goalSelectedId),
            contentColor = getContentColor(
                goalId = EnumGoal.GAINMUSCLE, currentGoal = goalSelectedId
            ),
            enabled = isChooseGoalEnabled
        ) {
            onGoalSelect(EnumGoal.GAINMUSCLE)
            openDesiredWeightPickerBottomSheet()
        }
        RoundedIconButton(
            modifier = modifier.align(alignment = Alignment.TopEnd),
            iconRes = R.drawable.ic_weight,
            buttonText = LOSE_WEIGHT,
            bgColor = getBgColor(goalId = EnumGoal.LOSEWEIGHT, currentGoal = goalSelectedId),
            contentColor = getContentColor(
                goalId = EnumGoal.LOSEWEIGHT, currentGoal = goalSelectedId
            ),
            enabled = isChooseGoalEnabled
        ) {
            onGoalSelect(EnumGoal.LOSEWEIGHT)
            openDesiredWeightPickerBottomSheet()
        }
        RoundedIconButton(
            modifier = modifier.align(alignment = Alignment.BottomStart),
            iconRes = R.drawable.ic_heart,
            buttonText = GET_HEALTHIER,
            bgColor = getBgColor(goalId = EnumGoal.GETHEALTHIER, currentGoal = goalSelectedId),
            contentColor = getContentColor(
                goalId = EnumGoal.GETHEALTHIER, currentGoal = goalSelectedId
            ),
            enabled = isChooseGoalEnabled
        ) {
            onGoalSelect(EnumGoal.GETHEALTHIER)
        }
        RoundedIconButton(
            modifier = modifier.align(alignment = Alignment.BottomEnd),
            iconRes = R.drawable.ic_mood_happy,
            buttonText = IMPROVE_MOOD,
            bgColor = getBgColor(goalId = EnumGoal.IMPROVEMOOD, currentGoal = goalSelectedId),
            contentColor = getContentColor(
                goalId = EnumGoal.IMPROVEMOOD, currentGoal = goalSelectedId
            ),
            enabled = isChooseGoalEnabled
        ) { onGoalSelect(EnumGoal.IMPROVEMOOD) }
    }
}

@Composable
fun RoundedIconButton(
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    buttonText: String,
    bgColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.size(130.dp),
        shape = CircleShape,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = bgColor, contentColor = contentColor
        ),
        enabled = enabled
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(id = iconRes), contentDescription = null)
            Text(
                text = buttonText,
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun getBgColor(goalId: EnumGoal, currentGoal: EnumGoal): Color {
    return if (goalId == currentGoal) {
        MaterialTheme.colors.primary
    } else MaterialTheme.colors.secondaryVariant
}

@Composable
private fun getContentColor(goalId: EnumGoal, currentGoal: EnumGoal): Color {
    return if (goalId == currentGoal) {
        MaterialTheme.colors.onPrimary
    } else MaterialTheme.colors.onSecondary
}