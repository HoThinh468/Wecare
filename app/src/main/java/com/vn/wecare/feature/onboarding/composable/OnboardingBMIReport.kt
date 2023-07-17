package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.onboarding.composable.dialog.OnboardingBMIRecommendationDialog
import com.vn.wecare.feature.onboarding.model.BMIState
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.bmiFormatWithFloat

@Composable
fun OnboardingBMIReport(
    modifier: Modifier, bmi: Float, gender: Boolean, recommendedGoal: EnumGoal, bmiState: BMIState
) {

    val openDetailDialog = remember {
        mutableStateOf(true)
    }

    if (openDetailDialog.value) {
        OnboardingBMIRecommendationDialog(
            modifier = modifier,
            onDismissDialog = { openDetailDialog.value = false },
            bmiState = bmiState
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(midPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = modifier.padding(vertical = smallPadding),
            text = "Your BMI: ${bmiFormatWithFloat(bmi)}",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = modifier.padding(horizontal = mediumPadding),
            text = "You seem to be ${bmiState.value.lowercase()}",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(mediumPadding))
        Image(
            modifier = modifier
                .fillMaxWidth()
                .clip(Shapes.large), painter = painterResource(
                id = if (gender) R.drawable.img_bmi_men else R.drawable.img_bmi_women
            ), contentDescription = null, contentScale = ContentScale.Fit
        )
        Spacer(modifier = modifier.height(mediumPadding))
        Text(
            text = "Our prediction: Your goal should be",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = recommendedGoal.value.uppercase(),
            style = MaterialTheme.typography.h3.copy(MaterialTheme.colors.primary),
            textAlign = TextAlign.Center
        )
    }
}