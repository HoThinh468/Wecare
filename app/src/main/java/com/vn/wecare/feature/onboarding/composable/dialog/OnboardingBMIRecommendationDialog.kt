package com.vn.wecare.feature.onboarding.composable.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.feature.onboarding.model.BMIState
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding

@Composable
fun OnboardingBMIRecommendationDialog(
    modifier: Modifier, onDismissDialog: () -> Unit, bmiState: BMIState
) {
    Dialog(onDismissRequest = { onDismissDialog() }) {
        Card(
            shape = Shapes.large
        ) {
            Column(
                modifier = modifier
                    .padding(midPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = "Our recommendation",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
                Image(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = midPadding),
                    painter = painterResource(id = R.drawable.img_recommendation),
                    contentDescription = null
                )
                Text(
                    text = "What you should know?",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.bmi_definition),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = stringResource(id = getStatusDescription(bmiState)),
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = modifier.height(halfMidPadding))
                Text(
                    text = if (bmiState == BMIState.NORMAL) "Benefits" else "Health problems",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = getStateHealthProblemsOrBenefits(bmiState)),
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = modifier.height(halfMidPadding))
                Text(
                    text = "Tips for you", style = MaterialTheme.typography.body1
                )
                Text(
                    text = stringResource(id = getStatusTips(bmiState)),
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = modifier.height(halfMidPadding))
                Button(modifier = modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                    onClick = { onDismissDialog() }) {
                    Text(text = "I understand", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}

private fun getStatusDescription(state: BMIState): Int {
    return when (state) {
        BMIState.UNDERWEIGHT -> R.string.underweight_description
        BMIState.NORMAL -> R.string.normal_description
        else -> R.string.overweight_description
    }
}

private fun getStateHealthProblemsOrBenefits(state: BMIState): Int {
    return when (state) {
        BMIState.UNDERWEIGHT -> R.string.underweight_health_problems
        BMIState.NORMAL -> R.string.normal_bmi_benefits
        else -> R.string.overweight_health_problems
    }
}

private fun getStatusTips(state: BMIState): Int {
    return when (state) {
        BMIState.UNDERWEIGHT -> R.string.tips_for_underweight
        BMIState.NORMAL -> R.string.tips_for_normal
        else -> R.string.tips_for_overweight
    }
}