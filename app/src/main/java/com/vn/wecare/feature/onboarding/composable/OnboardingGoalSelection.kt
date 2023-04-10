package com.vn.wecare.feature.onboarding.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.onboarding.viewmodel.OnboardingViewModel
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_MUSCLE
import com.vn.wecare.utils.WecareUserConstantValues.GET_HEALTHIER
import com.vn.wecare.utils.WecareUserConstantValues.IMPROVE_MOOD
import com.vn.wecare.utils.WecareUserConstantValues.LOOSE_WEIGHT

@Composable
fun OnboardingGoalSelection(
    modifier: Modifier, viewModel: OnboardingViewModel
) {

    val uiState = viewModel.onboardingUiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding)
            .height(300.dp)
    ) {
        SquareIconButton(
            modifier = modifier.align(alignment = Alignment.TopStart),
            iconRes = R.drawable.ic_muscle,
            buttonText = GAIN_MUSCLE,
            bgColor = getBgColor(id = 0, uiId = uiState.value.goalSelectionId),
            contentColor = getContentColor(id = 0, uiId = uiState.value.goalSelectionId),
        ) { viewModel.onGoalSelect(0) }
        SquareIconButton(
            modifier = modifier.align(alignment = Alignment.TopEnd),
            iconRes = R.drawable.ic_weight,
            buttonText = LOOSE_WEIGHT,
            bgColor = getBgColor(id = 1, uiId = uiState.value.goalSelectionId),
            contentColor = getContentColor(id = 1, uiId = uiState.value.goalSelectionId),
        ) { viewModel.onGoalSelect(1) }
        SquareIconButton(
            modifier = modifier.align(alignment = Alignment.BottomStart),
            iconRes = R.drawable.ic_heart,
            buttonText = GET_HEALTHIER,
            bgColor = getBgColor(id = 2, uiId = uiState.value.goalSelectionId),
            contentColor = getContentColor(id = 2, uiId = uiState.value.goalSelectionId),
        ) { viewModel.onGoalSelect(2) }
        SquareIconButton(
            modifier = modifier.align(alignment = Alignment.BottomEnd),
            iconRes = R.drawable.ic_mood_happy,
            buttonText = IMPROVE_MOOD,
            bgColor = getBgColor(id = 3, uiId = uiState.value.goalSelectionId),
            contentColor = getContentColor(id = 3, uiId = uiState.value.goalSelectionId),
        ) { viewModel.onGoalSelect(3) }
    }
}

@Composable
fun SquareIconButton(
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    buttonText: String,
    bgColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.size(130.dp),
        shape = CircleShape,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = bgColor, contentColor = contentColor)
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
private fun getBgColor(id: Int, uiId: Int): Color {
    return if (id == uiId) {
        MaterialTheme.colors.primary
    } else MaterialTheme.colors.secondaryVariant
}

@Composable
private fun getContentColor(id: Int, uiId: Int): Color {
    return if (id == uiId) {
        MaterialTheme.colors.onPrimary
    } else MaterialTheme.colors.onSecondary
}