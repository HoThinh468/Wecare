package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.onboarding.model.FemaleModelInfo
import com.vn.wecare.feature.onboarding.model.MaleModelInfo
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun OnboardingWeightToLosePicker(
    modifier: Modifier,
    desiredWeightDifference: Int,
    chosenGoal: EnumGoal,
    openPickWeightBottomSheet: () -> Unit,
    gender: Boolean,
    height: Int,
    warningMessage: String? = null
) {

    val maleModel = getMaleImgSrc(height)
    val femaleModel = getFemaleImgSrc(height)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = modifier.height(mediumPadding))
        if (gender && maleModel != null) {
            Text(text = "Celebrity you may know", style = MaterialTheme.typography.h2)
            Spacer(modifier = modifier.height(normalPadding))
            Image(
                modifier = modifier
                    .height(250.dp)
                    .clip(Shapes.medium),
                painter = painterResource(id = maleModel.imgSrc),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = modifier.height(halfMidPadding))
            Text(text = maleModel.fullName.uppercase(), style = MaterialTheme.typography.h3)
            Text(text = "${maleModel.height} cm - ${maleModel.weight} kg -> BMI: ${maleModel.bmi}")
        } else if (femaleModel != null) {
            Text(text = "Celebrity you may know", style = MaterialTheme.typography.h2)
            Spacer(modifier = modifier.height(normalPadding))
            Image(
                modifier = modifier
                    .height(250.dp)
                    .clip(Shapes.medium),
                painter = painterResource(id = femaleModel.imgSrc),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = modifier.height(halfMidPadding))
            Text(text = femaleModel.fullName.uppercase(), style = MaterialTheme.typography.h3)
            Text(
                text = "${femaleModel.height} cm - ${femaleModel.weight} kg, BMI: ${femaleModel.bmi}",
                style = MaterialTheme.typography.body2
            )
        }
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
    }
}

private fun getMaleImgSrc(height: Int): MaleModelInfo? {
    return when (height) {
        in 160..170 -> MaleModelInfo.BRUNOMARS
        in 171..180 -> MaleModelInfo.TOMHOLLAND
        in 181..190 -> MaleModelInfo.CHRISPINE
        in 191..200 -> MaleModelInfo.CHRISHEMSWORTH
        else -> null
    }
}

private fun getFemaleImgSrc(height: Int): FemaleModelInfo? {
    return when (height) {
        in 150..160 -> FemaleModelInfo.ARIANAGRANDE
        in 161..170 -> FemaleModelInfo.SCARLETJOHANSSON
        in 171..180 -> FemaleModelInfo.TAYLORSWIFT
        else -> null
    }
}